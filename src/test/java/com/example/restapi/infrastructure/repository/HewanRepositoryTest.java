package com.example.restapi.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.restapi.domain.services.HewanServices;
import com.example.restapi.infrastructure.entity.Hewan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class HewanRepositoryTest {
    @Mock
    private HewanRepository hewanRepositoryMock;

    @InjectMocks
    private HewanServices hewanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByHewanIdExist() {
        String hewanId = "jenis_hewan|0001";
        Hewan hewan1 = new Hewan(UUID.randomUUID(), hewanId, "karnivora", "Kucing", "Daging", 3);
        List<Hewan> hewanList = new ArrayList<>();
        hewanList.add(hewan1);

        when(hewanRepositoryMock.findByHewanId(hewanId)).thenReturn(hewanList);

        List<Hewan> result = hewanService.findByHewanId(hewanId);

        assertEquals(1, result.size());
        assertEquals(hewanId, result.get(0).getHewanId());
        assertEquals("Kucing", result.get(0).getNama());
        assertEquals("Daging", result.get(0).getMakanan());
        assertEquals(3, result.get(0).getJumlah());
    }

    @Test
    public void testFindByHewanIdDoesNotExist() {
        String hewanId = "jenis_hewan|0001";
        when(hewanRepositoryMock.findByHewanId(hewanId)).thenReturn(new ArrayList<>());
        List<Hewan> result = hewanService.findByHewanId(hewanId);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllHewanExist() {
        List<Hewan> hewanList = new ArrayList<>();
        hewanList.add(new Hewan(UUID.randomUUID(), "jenis_hewan|0001", "karnivora", "Kucing", "Daging", 3));
        hewanList.add(new Hewan(UUID.randomUUID(), "jenis_hewan|0002", "karnivora", "Anjing", "Tulang", 5));
        when(hewanRepositoryMock.findAll()).thenReturn(hewanList);
        List<Hewan> result = hewanService.findAllHewan();
        assertEquals(0, result.size());
    }

    @Test
    public void testFindAllHewanDoNotExist() {
        when(hewanRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        List<Hewan> result = hewanService.findAllHewan();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testDeleteByHewanIdExists() {
        String hewanId = "jenis_hewan|0001";
        Hewan hewan = new Hewan(UUID.randomUUID(), hewanId, "karnivora", "Kucing", "Daging", 3);
        when(hewanRepositoryMock.findByHewanId(hewanId)).thenReturn(List.of(hewan));
        hewanService.deleteByHewanId(hewanId);
        verify(hewanRepositoryMock, times(1)).deleteByHewanId(hewanId);
    }

    @Test
    public void testDeleteByHewanIdDoesNotExist() {
        String hewanId = null;
        when(hewanRepositoryMock.findByHewanId(hewanId)).thenReturn(new ArrayList<>());
        hewanService.deleteByHewanId(hewanId);
        verify(hewanRepositoryMock).deleteByHewanId(hewanId);
    }
}
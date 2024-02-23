package com.example.restapi.domain.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.restapi.infrastructure.entity.Hewan;
import com.example.restapi.infrastructure.repository.HewanRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class HewanServicesTest {
    @Mock
    private HewanRepository hewanRepository;

    @InjectMocks
    private HewanServices hewanServices;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Hewan hewan = new Hewan();
        hewan.setHewanId("jenis_hewan|0001");
        hewan.setId(UUID.randomUUID());
        hewan.setJenisHewan("Jenis Hewan");
        hewan.setJumlah(1);
        hewan.setMakanan("Makanan");
        hewan.setNama("Nama");

        when(hewanRepository.save(Mockito.<Hewan>any())).thenReturn(hewan);

        Hewan hewan2 = new Hewan();
        hewan2.setHewanId("jenis_hewan|0001");
        hewan2.setId(UUID.randomUUID());
        hewan2.setJenisHewan("Jenis Hewan");
        hewan2.setJumlah(1);
        hewan2.setMakanan("Makanan");
        hewan2.setNama("Nama");
        Hewan actualSaveResult = hewanServices.save(hewan2);

        verify(hewanRepository).save(Mockito.<Hewan>any());
        assertSame(hewan, actualSaveResult);
    }

    @Test
    void testFindByHewanId() {
        ArrayList<Hewan> hewanList = new ArrayList<>();
        when(hewanRepository.findByHewanId(Mockito.<String>any())).thenReturn(hewanList);
        List<Hewan> actualFindByHewanIdResult = hewanServices.findByHewanId("jenis_hewan|0001");
        verify(hewanRepository).findByHewanId(Mockito.<String>any());
        assertTrue(actualFindByHewanIdResult.isEmpty());
        assertSame(hewanList, actualFindByHewanIdResult);
    }

    @Test
    void testFindAllHewan() {
        ArrayList<Hewan> hewanList = new ArrayList<>();
        when(hewanRepository.findAllHewan()).thenReturn(hewanList);
        List<Hewan> actualFindAllHewanResult = hewanServices.findAllHewan();
        verify(hewanRepository).findAllHewan();
        assertTrue(actualFindAllHewanResult.isEmpty());
        assertSame(hewanList, actualFindAllHewanResult);
    }

    @Test
    void testDeleteByHewanId() {
        doNothing().when(hewanRepository).deleteByHewanId(Mockito.<String>any());
        hewanServices.deleteByHewanId("jenis_hewan|0001");
        verify(hewanRepository).deleteByHewanId(Mockito.<String>any());
    }
}

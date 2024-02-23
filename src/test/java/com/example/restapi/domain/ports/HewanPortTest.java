package com.example.restapi.domain.ports;

import com.example.restapi.domain.services.HewanServices;
import com.example.restapi.infrastructure.entity.Hewan;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class HewanPortTest {
    @Mock
    private HewanPort hewanPort;

    @InjectMocks
    private HewanServices hewanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveHewan() {
        Hewan hewan = new Hewan();
        when(hewanPort.save(hewan)).thenReturn(hewan);
        Hewan savedHewan = hewanService.save(hewan);
        assertEquals(hewan, savedHewan);
        verify(hewanPort, times(1)).save(hewan);
    }

    @Test
    public void testFindByHewanId() {
        String hewanId = "jenis_hewan|0001";
        List<Hewan> hewanList = new ArrayList<>();
        when(hewanPort.findByHewanId(hewanId)).thenReturn(hewanList);
        List<Hewan> foundHewanList = hewanService.findByHewanId(hewanId);
        assertEquals(hewanList, foundHewanList);
        verify(hewanPort, times(1)).findByHewanId(hewanId);
    }

    @Test
    public void testFindAllHewan() {
        List<Hewan> hewanList = new ArrayList<>();
        when(hewanPort.findAllHewan()).thenReturn(hewanList);
        List<Hewan> foundHewanList = hewanService.findAllHewan();
        assertEquals(hewanList, foundHewanList);
        verify(hewanPort, times(1)).findAllHewan();
    }

    @Test
    public void testDeleteByHewanId() {
        String hewanId = "jenis_hewan|0001";
        hewanService.deleteByHewanId(hewanId);
        verify(hewanPort, times(1)).deleteByHewanId(hewanId);
    }
}

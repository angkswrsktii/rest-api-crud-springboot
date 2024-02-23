package com.example.restapi.infrastructure.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HewanTest {
    @Test
    void testHewan() {
        Hewan hewan = new Hewan();
        UUID id = UUID.randomUUID();
        hewan.setId(id);
        hewan.setHewanId("jenis_hewan|0001");
        hewan.setJenisHewan("Kucing");
        hewan.setNama("Fluffy");
        hewan.setMakanan("Whiskas");
        hewan.setJumlah(2);

        assertEquals(id, hewan.getId());
        assertEquals("jenis_hewan|0001", hewan.getHewanId());
        assertEquals("Kucing", hewan.getJenisHewan());
        assertEquals("Fluffy", hewan.getNama());
        assertEquals("Whiskas", hewan.getMakanan());
        assertEquals(2, hewan.getJumlah());
    }
}

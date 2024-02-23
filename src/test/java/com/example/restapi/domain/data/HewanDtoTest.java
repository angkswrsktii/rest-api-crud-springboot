package com.example.restapi.domain.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HewanDtoTest {
    @Test
     void testHewanDto(){
        HewanDto hewan = new HewanDto();
        String expectedName = "Kucing";
        String expectedMakanan = "Whiskas";
        int expectedJumlah = 2;

        hewan.setNama(expectedName);
        hewan.setMakanan(expectedMakanan);
        hewan.setJumlah(expectedJumlah);

        assertEquals(expectedName, hewan.getNama());
        assertEquals(expectedMakanan, hewan.getMakanan());
        assertEquals(expectedJumlah, hewan.getJumlah());
    }
}

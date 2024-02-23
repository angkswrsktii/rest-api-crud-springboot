package com.example.restapi.domain.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class HewanRequestDtoTest {
    @Test
     void testHewanRequestDto() {
        HewanRequestDto hewanRequest = new HewanRequestDto();

        String expectedHewanId = "jenis_hewan|0001";
        Map<String, HewanDto> expectedHewan = new HashMap<>();
        expectedHewan.put("kucing", new HewanDto("Kucing", "Whiskas", 2));

        hewanRequest.setHewanId(expectedHewanId);
        hewanRequest.setHewan(expectedHewan);

        assertEquals(expectedHewanId, hewanRequest.getHewanId());

        HewanDto hewanDto = hewanRequest.getHewan().get("kucing");

        assertEquals("Kucing", hewanDto.getNama());
        assertEquals("Whiskas", hewanDto.getMakanan());
        assertEquals(2, hewanDto.getJumlah());

    }
}

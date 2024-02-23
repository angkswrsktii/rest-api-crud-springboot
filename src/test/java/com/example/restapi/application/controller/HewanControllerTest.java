package com.example.restapi.application.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.example.restapi.domain.data.HewanDto;
import com.example.restapi.domain.data.HewanRequestDto;
import com.example.restapi.domain.services.HewanServices;
import com.example.restapi.infrastructure.entity.Hewan;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


public class HewanControllerTest {
    @Mock
    private HewanServices hewanServices;

    @InjectMocks
    private HewanController hewanController;

    String authorization;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateHewanSuccess() {
        HewanRequestDto requestDto = new HewanRequestDto();
        requestDto.setHewanId("jenis_hewan|0001");
        Map<String, HewanDto> hewanMap = new HashMap<>();
        hewanMap.put("kucing", new HewanDto("Kucing", "Whiskas", 2));
        requestDto.setHewan(hewanMap);
        when(hewanServices.save(any(Hewan.class))).thenReturn(new Hewan());
        ResponseEntity<?> response = hewanController.createHewan(requestDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testGetHewanByIdSuccess() {
        HewanRequestDto hewanRequestDto = new HewanRequestDto();
        hewanRequestDto.setHewanId("jenis_hewan|0001");
        List<Hewan> hewanList = new ArrayList<>();
        hewanList.add(new Hewan());
        when(hewanServices.findByHewanId("jenis_hewan|0001")).thenReturn(hewanList);
        ResponseEntity<?> response = hewanController.getHewanById(hewanRequestDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllHewanSuccess() {
        List<Hewan> hewanList = new ArrayList<>();
        hewanList.add(new Hewan());
        when(hewanServices.findAllHewan()).thenReturn(hewanList);
        ResponseEntity<?> response = hewanController.getAllHewan();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteHewanById_Success() {
        HewanRequestDto hewanRequestDto = new HewanRequestDto();
        hewanRequestDto.setHewanId("jenis_hewan|0001");
        List<Hewan> hewanList = new ArrayList<>();
        hewanList.add(new Hewan());
        when(hewanServices.findByHewanId("jenis_hewan|0001")).thenReturn(hewanList);
        ResponseEntity<?> response = hewanController.deleteHewanById(hewanRequestDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateHewanFailed() throws Exception {
        when(hewanServices.findByHewanId(Mockito.<String>any())).thenReturn(new ArrayList<>());

        HewanRequestDto hewanRequestDto = new HewanRequestDto();
        hewanRequestDto.setHewan(new HashMap<>());
        hewanRequestDto.setHewanId("jenis_hewan|0001");
        String content = (new ObjectMapper()).writeValueAsString(hewanRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/hewan/updateById")
                .param("hewan_id", "foo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(hewanController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status_code\":\"500\",\"status_desc\":\"FAILED\",\"response_time\":\"48\"}"));
    }

    @Test
    void testUpdateHewanSuccess() throws Exception {
        Hewan hewan = new Hewan();
        hewan.setHewanId("42");
        hewan.setId(UUID.randomUUID());
        hewan.setJenisHewan("Omnivora");
        hewan.setJumlah(1);
        hewan.setMakanan("Buah");
        hewan.setNama("Kelelawar");

        ArrayList<Hewan> hewanList = new ArrayList<>();
        hewanList.add(hewan);
        when(hewanServices.findByHewanId(Mockito.<String>any())).thenReturn(hewanList);

        HewanRequestDto hewanRequestDto = new HewanRequestDto();
        hewanRequestDto.setHewan(new HashMap<>());
        hewanRequestDto.setHewanId("42");
        String content = (new ObjectMapper()).writeValueAsString(hewanRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/hewan/updateById")
                .param("hewan_id", "foo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(hewanController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status_code\":\"200\",\"status_desc\":\"SUCCESS\",\"response_time\":\"48\"}"));
    }
}




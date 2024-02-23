package com.example.restapi.infrastructure.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HewanHttpStatusTest {
    @Test
    void whenGetSuccess() {
        HewanHttpStatus valueOfResult = HewanHttpStatus.valueOf("SUCCESS");
        String responseTime = valueOfResult.getResponseTime();
        String statusCode = valueOfResult.getStatusCode();
        assertEquals("200", statusCode);
        assertEquals("48", responseTime);
        assertEquals("SUCCESS", valueOfResult.getStatusDesc());
    }

    @Test
    void whenGetFailed() {
        HewanHttpStatus valueOfResult = HewanHttpStatus.valueOf("FAILED");
        String responseTime = valueOfResult.getResponseTime();
        String statusCode = valueOfResult.getStatusCode();
        assertEquals("500", statusCode);
        assertEquals("48", responseTime);
        assertEquals("FAILED", valueOfResult.getStatusDesc());
    }

    @Test
    void whenGetStatusCodeSuccess() {
        assertEquals("200", HewanHttpStatus.SUCCESS.getStatusCode());
    }

    @Test
    void whenGetStatusDescSuccess() {
        assertEquals("SUCCESS", HewanHttpStatus.SUCCESS.getStatusDesc());
    }

    @Test
    void whenGetResponseTimeSuccess() {
        assertEquals("48", HewanHttpStatus.SUCCESS.getResponseTime());
    }

    @Test
    void whenGetStatusCodeFailed() {
        assertEquals("500", HewanHttpStatus.FAILED.getStatusCode());
    }

    @Test
    void whenGetStatusDescFailed() {
        assertEquals("FAILED", HewanHttpStatus.FAILED.getStatusDesc());
    }

    @Test
    void whenGetResponseTimeFailed() {
        assertEquals("48", HewanHttpStatus.FAILED.getResponseTime());
    }
}

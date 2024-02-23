package com.example.restapi.application.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.restapi.infrastructure.util.HewanHttpStatus;
import org.junit.jupiter.api.Test;

class BaseResponseTest {
    @Test
    void whenResponseSuccess() {
        BaseResponse actualBaseResponse = new BaseResponse(HewanHttpStatus.SUCCESS);
        assertEquals("200", actualBaseResponse.getStatusCode());
        assertEquals("48", actualBaseResponse.getResponseTime());
        assertEquals("SUCCESS", actualBaseResponse.getStatusDesc());
    }

    @Test
    void whenResponseFailed() {
        BaseResponse actualBaseResponse = new BaseResponse(HewanHttpStatus.FAILED);
        assertEquals("48", actualBaseResponse.getResponseTime());
        assertEquals("500", actualBaseResponse.getStatusCode());
        assertEquals("FAILED", actualBaseResponse.getStatusDesc());
    }
}

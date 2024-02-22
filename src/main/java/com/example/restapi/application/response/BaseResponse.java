package com.example.restapi.application.response;

import com.example.restapi.infrastructure.util.HewanHttpStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("status_desc")
    private String statusDesc;

    @JsonProperty("response_time")
    private String responseTime;

    public BaseResponse(HewanHttpStatus hewanHttpStatus) {
        this.statusCode = hewanHttpStatus.getStatusCode();
        this.statusDesc = hewanHttpStatus.getStatusDesc();
        this.responseTime = hewanHttpStatus.getResponseTime();
    }
}

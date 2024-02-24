package com.example.restapi.infrastructure.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum HewanHttpStatus {
    SUCCESS("200", "SUCCESS", "48"),
    FAILED("500", "FAILED", "48");

    private String statusCode;
    private String statusDesc;
    private String responseTime;
}

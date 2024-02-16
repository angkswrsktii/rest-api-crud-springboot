package com.example.restapi.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KendaraanRequestDto {
    private String kendaraanId;
    private Map<String, KendaraanDto> kendaraan;
}

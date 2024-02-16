package com.example.restapi.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KendaraanDto {
    private String kendaraanId;
    private String nama;
    private String bensin;
    private int jumlah;
}

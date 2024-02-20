package com.example.restapi.domain.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KendaraanDto {
    private String jenisKendaraan;
    private String nama;
    private String bensin;
    private int jumlah;
}

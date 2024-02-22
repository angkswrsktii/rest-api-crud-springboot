package com.example.restapi.domain.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HewanDto {
    private String jenisKendaraan;
    private String nama;
    private String makanan;
    private int jumlah;
}

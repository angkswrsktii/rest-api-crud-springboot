package com.example.restapi.adapter.persistence.entity;

import com.example.restapi.adapter.dto.KendaraanDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "kendaraan")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Kendaraan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "kendaraan_id")
    private String kendaraanId;
    @Column(name = "jenis_kendaraan")
    private String jenisKendaraan;
    private String nama;
    private String bensin;
    private int jumlah;
}
package com.example.restapi.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "hewan")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Hewan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "hewan_id")
    private String hewanId;
    @Column(name = "jenis_hewan")
    private String jenisHewan;
    private String nama;
    private String makanan;
    private int jumlah;
}
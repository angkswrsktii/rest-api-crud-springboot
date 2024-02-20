package com.example.restapi.domain.ports;

import com.example.restapi.infrastructure.entity.Kendaraan;

import java.util.List;

public interface KendaraanPort {
    Kendaraan save(Kendaraan kendaraan);
    List<Kendaraan> findByKendaraanId(String kendaraanId);
    List<Kendaraan> findAllKendaraan();
    void deleteByKendaraanId(String kendaraanId);
}
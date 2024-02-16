package com.example.restapi.adapter.persistence.service;

import com.example.restapi.adapter.persistence.entity.Kendaraan;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KendaraanService {
    Kendaraan save(Kendaraan kendaraan);
    List<Kendaraan> findByKendaraanId(String kendaraanId);
    List<Kendaraan> findAllKendaraan();
    void deleteByKendaraanId(String kendaraanId);
}
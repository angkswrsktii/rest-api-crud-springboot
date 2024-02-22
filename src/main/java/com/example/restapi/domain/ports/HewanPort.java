package com.example.restapi.domain.ports;

import com.example.restapi.infrastructure.entity.Hewan;

import java.util.List;

public interface HewanPort {
    Hewan save(Hewan hewan);
    List<Hewan> findByHewanId(String hewanId);
    List<Hewan> findAllHewan();
    void deleteByHewanId(String hewanId);
}
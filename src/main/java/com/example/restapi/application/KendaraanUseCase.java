package com.example.restapi.application;

import com.example.restapi.adapter.persistence.entity.Kendaraan;
import com.example.restapi.adapter.persistence.repository.KendaraanRepository;
import com.example.restapi.adapter.persistence.service.KendaraanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KendaraanUseCase implements KendaraanService {
    private final KendaraanRepository kendaraanRepository;
    public KendaraanUseCase(KendaraanRepository kendaraanRepository) {
        this.kendaraanRepository = kendaraanRepository;
    }
    @Override
    public Kendaraan save(Kendaraan kendaraan) {
        return kendaraanRepository.save(kendaraan);
    }
    @Override
    public List<Kendaraan> findByKendaraanId(String kendaraanId) {return kendaraanRepository.findByKendaraanId(kendaraanId);}
    @Override
    public List<Kendaraan> findAllKendaraan() {
        return kendaraanRepository.findAllKendaraan();
    }
    @Override
    public void deleteByKendaraanId(String kendaraanId) {
        kendaraanRepository.deleteByKendaraanId(kendaraanId);
    }
}

package com.example.restapi.application.controller;

import com.example.restapi.domain.data.KendaraanDto;
import com.example.restapi.domain.data.KendaraanRequestDto;
import com.example.restapi.infrastructure.entity.Kendaraan;
import com.example.restapi.domain.services.KendaraanUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kendaraan")
public class KendaraanController {

    private final KendaraanUseCase kendaraanUseCase;

    public KendaraanController(KendaraanUseCase kendaraanUseCase) {
        this.kendaraanUseCase = kendaraanUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createKendaraan(@RequestBody KendaraanRequestDto requestDto) {
        try {
            String kendaraanId = requestDto.getKendaraanId();

            for (Map.Entry<String, KendaraanDto> entry : requestDto.getKendaraan().entrySet()) {
                String jenisKendaraan = entry.getKey();
                KendaraanDto kendaraanDto = entry.getValue();

                Kendaraan kendaraan = new Kendaraan();
                kendaraan.setKendaraanId(kendaraanId);
                kendaraan.setJenisKendaraan(jenisKendaraan);
                kendaraan.setNama(kendaraanDto.getNama());
                kendaraan.setBensin(kendaraanDto.getBensin());
                kendaraan.setJumlah(kendaraanDto.getJumlah());

                kendaraanUseCase.save(kendaraan);
            }

            return ResponseEntity.ok().body("Kendaraan berhasil disimpan");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal menyimpan kendaraan: " + e.getMessage());
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getKendaraanById(@RequestParam("kendaraan_id") String kendaraanId) {
        try {
            List<Kendaraan> kendaraanList = kendaraanUseCase.findByKendaraanId(kendaraanId);
            if (!kendaraanList.isEmpty()) {
                return ResponseEntity.ok().body(kendaraanList);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kendaraan tidak ditemukan");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal mengambil kendaraan: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Kendaraan>> getAllKendaraan() {
        try {
            List<Kendaraan> allKendaraan = kendaraanUseCase.findAllKendaraan();
            return ResponseEntity.ok().body(allKendaraan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PutMapping("/updateById")
    public ResponseEntity<?> updateKendaraan(@RequestParam("kendaraan_id") String kendaraanId,
                                             @RequestBody KendaraanRequestDto requestDto) 
    {
        try {
            List<Kendaraan> kendaraanList = kendaraanUseCase.findByKendaraanId(kendaraanId);
            if (kendaraanList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Kendaraan dengan ID " + kendaraanId + " tidak ditemukan");
            }

            for (Map.Entry<String, KendaraanDto> entry : requestDto.getKendaraan().entrySet()) {
                String jenisKendaraan = entry.getKey();
                KendaraanDto kendaraanDto = entry.getValue();
                Kendaraan kendaraanToUpdate = null;
                
                for (Kendaraan kendaraan : kendaraanList) {
                    if (kendaraan.getJenisKendaraan().equals(jenisKendaraan)) {
                        kendaraanToUpdate = kendaraan;
                        break;
                    }
                }

                if (kendaraanToUpdate != null) {
                    kendaraanToUpdate.setNama(kendaraanDto.getNama());
                    kendaraanToUpdate.setBensin(kendaraanDto.getBensin());
                    kendaraanToUpdate.setJumlah(kendaraanDto.getJumlah());
                    
                    kendaraanUseCase.save(kendaraanToUpdate);
                }
            }

            return ResponseEntity.ok().body("Kendaraan dengan ID " + kendaraanId + " berhasil diperbarui");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal memperbarui kendaraan: " + e.getMessage());
        }
    }



    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteKendaraanById(@RequestParam("kendaraan_id") String kendaraanId) {
        try {
            kendaraanUseCase.deleteByKendaraanId(kendaraanId);
            return ResponseEntity.ok().body("Kendaraan dengan id " + kendaraanId + " berhasil dihapus");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal menghapus kendaraan: " + e.getMessage());
        }
    }
}

package com.example.restapi.application.controller;

import com.example.restapi.application.response.BaseResponse;
import com.example.restapi.domain.data.HewanDto;
import com.example.restapi.domain.data.HewanRequestDto;
import com.example.restapi.domain.services.HewanServices;
import com.example.restapi.infrastructure.entity.Hewan;
import com.example.restapi.infrastructure.util.HewanHttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hewan")
public class HewanController {

    private final HewanServices hewanServices;

    public HewanController(HewanServices hewanServices) {
        this.hewanServices = hewanServices;
    }

    @PostMapping
    public ResponseEntity<?> createHewan(@RequestBody HewanRequestDto requestDto) {
        try {
            String hewanId = requestDto.getHewanId();

            for (Map.Entry<String, HewanDto> entry : requestDto.getHewan().entrySet()) {
                String jenisHewan = entry.getKey();
                HewanDto hewanDto = entry.getValue();

                Hewan hewan = new Hewan();
                hewan.setHewanId(hewanId);
                hewan.setJenisHewan(jenisHewan);
                hewan.setNama(hewanDto.getNama());
                hewan.setMakanan(hewanDto.getMakanan());
                hewan.setJumlah(hewanDto.getJumlah());

                hewanServices.save(hewan);
            }

            BaseResponse response = new BaseResponse(HewanHttpStatus.SUCCESS);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getHewanById(@RequestParam("hewan_id") String hewanId) {
        try {
            List<Hewan> hewanList = hewanServices.findByHewanId(hewanId);
            if (!hewanList.isEmpty()) {
                return ResponseEntity.ok().body(hewanList);
            } else {
                BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllHewan() {
        try {
            List<Hewan> allHewan = hewanServices.findAllHewan();
            if (!allHewan.isEmpty()) {
                return ResponseEntity.ok().body(allHewan);
            }
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/updateById")
    public ResponseEntity<?> updateHewan(@RequestParam("hewan_id") String hewanId,
                                             @RequestBody HewanRequestDto requestDto)
    {
        try {
            List<Hewan> hewanList = hewanServices.findByHewanId(hewanId);
            if (hewanList.isEmpty()) {
                BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            for (Map.Entry<String, HewanDto> entry : requestDto.getHewan().entrySet()) {
                String jenisHewan = entry.getKey();
                HewanDto hewanDto = entry.getValue();
                Hewan hewanToUpdate = null;

                for (Hewan hewan : hewanList) {
                    if (hewan.getJenisHewan().equals(jenisHewan)) {
                        hewanToUpdate = hewan;
                        break;
                    }
                }

                if (hewanToUpdate != null) {
                    hewanToUpdate.setNama(hewanDto.getNama());
                    hewanToUpdate.setMakanan(hewanDto.getMakanan());
                    hewanToUpdate.setJumlah(hewanDto.getJumlah());

                    hewanServices.save(hewanToUpdate);
                }
            }

            BaseResponse response = new BaseResponse(HewanHttpStatus.SUCCESS);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteHewanById(@RequestParam("hewan_id") String hewanId) {
        try {
            List<Hewan> hewanList = hewanServices.findByHewanId(hewanId);
            if (hewanList.isEmpty()) {
                BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            hewanServices.deleteByHewanId(hewanId);
            BaseResponse response = new BaseResponse(HewanHttpStatus.SUCCESS);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

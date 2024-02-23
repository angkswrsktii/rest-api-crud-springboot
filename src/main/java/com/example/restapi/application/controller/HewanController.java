package com.example.restapi.application.controller;

import com.example.restapi.application.response.BaseResponse;
import com.example.restapi.domain.data.HewanDto;
import com.example.restapi.domain.data.HewanRequestDto;
import com.example.restapi.domain.services.HewanServices;
import com.example.restapi.infrastructure.entity.Hewan;
import com.example.restapi.infrastructure.util.HewanHttpStatus;
import com.example.restapi.infrastructure.util.Util;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
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
        } finally {
            Hewan hewan = new Hewan();
            HewanHttpStatus status = HewanHttpStatus.SUCCESS;
            String date = Util.getCurrentDate();
            String statusCode = HewanHttpStatus.getStatusCode(status);
            String statusDesc = HewanHttpStatus.getStatusDesc(status);
            Util.debugLogger.debug("date = {} | id = {} | status_code = {} | status_desc = {} | hewan_id = {} | nama = {} | jumlah = {}",
                    date, hewan.getId(), statusCode, statusDesc, hewan.getHewanId(), hewan.getNama(), hewan.getJumlah());
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getHewanById(@RequestBody HewanRequestDto hewanRequestDto) {
        try {
            String hewanId = hewanRequestDto.getHewanId();
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
    public ResponseEntity<?> deleteHewanById(@RequestBody HewanRequestDto hewanRequestDto) {
        try {
            String hewanId = hewanRequestDto.getHewanId();
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

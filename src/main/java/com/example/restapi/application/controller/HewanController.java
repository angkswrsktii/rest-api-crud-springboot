package com.example.restapi.application.controller;

import com.example.restapi.application.response.BaseResponse;
import com.example.restapi.domain.data.HewanDto;
import com.example.restapi.domain.data.HewanRequestDto;
import com.example.restapi.domain.ports.HewanPort;
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
    HewanRequestDto hewanRequestDto;
    Hewan hewanReq;
    public HewanController(HewanServices hewanServices) {
        this.hewanServices = hewanServices;
    }

    @PostMapping
    public ResponseEntity<?> createHewan(@RequestHeader(value = "Authorization") String authorization,
                                         @RequestBody HewanRequestDto requestDto) {
        try {
            if (!hewanServices.checkAuthorization(authorization, requestDto.toString())) {
                BaseResponse unauthorizedResponse = new BaseResponse(HewanHttpStatus.FAILED);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(unauthorizedResponse);
            }

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
                Util.debugLogger.debug("{}|{}|{}|{}|{}|{}|{}", Util.getCurrentDate(), hewanId, HewanHttpStatus.SUCCESS.getStatusCode(), HewanHttpStatus.SUCCESS.getStatusDesc(), hewan.getNama(), hewan.getJumlah());
            }
            BaseResponse response = new BaseResponse(HewanHttpStatus.SUCCESS);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getHewanById(@RequestHeader(value = "Authorization") String authorization,
                                          @RequestBody HewanRequestDto requestDto) {
        try {
            if (!hewanServices.checkAuthorization(authorization, requestDto.toString())) {
                BaseResponse unauthorizedResponse = new BaseResponse(HewanHttpStatus.FAILED);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(unauthorizedResponse);
            }
            String hewanId = requestDto.getHewanId();
            List<Hewan> hewanList = hewanServices.findByHewanId(hewanId);
            Util.debugLogger.debug("{}|{}|{}|{}|{}|{}|{}", Util.getCurrentDate(), hewanId, HewanHttpStatus.SUCCESS.getStatusCode(), HewanHttpStatus.SUCCESS.getStatusDesc(), hewanReq.getNama(), hewanReq.getJumlah());
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
    public ResponseEntity<?> getAllHewan(@RequestHeader(value = "Authorization") String authorization) {
        try {
            if (!hewanServices.checkAuthorization(authorization, String.valueOf(hewanRequestDto))) {
                BaseResponse unauthorizedResponse = new BaseResponse(HewanHttpStatus.FAILED);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(unauthorizedResponse);
            }
            String hewanId = hewanReq.getHewanId();
            List<Hewan> allHewan = hewanServices.findAllHewan();
            Util.debugLogger.debug("{}|{}|{}|{}|{}|{}|{}", Util.getCurrentDate(), hewanId, HewanHttpStatus.SUCCESS.getStatusCode(), HewanHttpStatus.SUCCESS.getStatusDesc(), hewanReq.getNama(), hewanReq.getJumlah());
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
    public ResponseEntity<?> updateHewan(@RequestHeader(value = "Authorization") String authorization,
                                         @RequestParam("hewan_id") String hewanId,
                                         @RequestBody HewanRequestDto requestDto) {
        try {
            if (!hewanServices.checkAuthorization(authorization, requestDto.toString())) {
                BaseResponse unauthorizedResponse = new BaseResponse(HewanHttpStatus.FAILED);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(unauthorizedResponse);
            }
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
                    Util.debugLogger.debug("{}|{}|{}|{}|{}|{}|{}", Util.getCurrentDate(), hewanId, HewanHttpStatus.SUCCESS.getStatusCode(), HewanHttpStatus.SUCCESS.getStatusDesc(), hewanToUpdate.getNama(), hewanToUpdate.getJumlah());
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
    public ResponseEntity<?> deleteHewanById(@RequestHeader(value = "Authorization") String authorization,
                                             @RequestBody HewanRequestDto requestDto) {
        try {
            if (!hewanServices.checkAuthorization(authorization, requestDto.toString())) {
                BaseResponse unauthorizedResponse = new BaseResponse(HewanHttpStatus.FAILED);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(unauthorizedResponse);
            }
            String hewanId = requestDto.getHewanId();
            List<Hewan> hewanList = hewanServices.findByHewanId(hewanId);
            if (hewanList.isEmpty()) {
                BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            hewanServices.deleteByHewanId(hewanId);
            Util.debugLogger.debug("{}|{}|{}|{}|{}|{}|{}", Util.getCurrentDate(), hewanId, HewanHttpStatus.SUCCESS.getStatusCode(), HewanHttpStatus.SUCCESS.getStatusDesc(), hewanReq.getNama(), hewanReq.getJumlah());
            BaseResponse response = new BaseResponse(HewanHttpStatus.SUCCESS);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

package com.example.restapi.application.controller;

import com.example.restapi.application.response.BaseResponse;
import com.example.restapi.domain.data.HewanDto;
import com.example.restapi.domain.data.HewanRequestDto;
import com.example.restapi.domain.services.HewanServices;
import com.example.restapi.infrastructure.entity.Hewan;
import com.example.restapi.infrastructure.util.HewanHttpStatus;
import com.example.restapi.infrastructure.util.Util;
import com.google.gson.Gson;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/hewan")
public class HewanController {
    private final HewanServices hewanServices;
    HewanRequestDto hewanRequestDto;
    Hewan hewanReq = new Hewan();
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

                Hewan savedHewan =  hewanServices.save(hewan);
                ThreadContext.put("id", String.valueOf(savedHewan.getId()));
                ThreadContext.put("statusCode", HewanHttpStatus.SUCCESS.getStatusCode());
                ThreadContext.put("statusDesc", HewanHttpStatus.SUCCESS.getStatusDesc());
                ThreadContext.put("hewanId", savedHewan.getHewanId());
                ThreadContext.put("nama", savedHewan.getNama());
                ThreadContext.put("jumlah", String.valueOf(savedHewan.getJumlah()));
            }
            BaseResponse response = new BaseResponse(HewanHttpStatus.SUCCESS);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } finally {
            Util.debugLogger.debug("{}|{}|{}|{}|{}|{}|{}", Util.getCurrentDate(), hewanReq.getId(),
                    ThreadContext.get("statusCode"), ThreadContext.get("statusDesc"),
                    ThreadContext.get("hewanId"), ThreadContext.get("nama"), ThreadContext.get("jumlah"));
            ThreadContext.clearAll();
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
            for (Hewan hewan : hewanList) {
                ThreadContext.put("id", String.valueOf(hewan.getId()));
                ThreadContext.put("statusCode", HewanHttpStatus.SUCCESS.getStatusCode());
                ThreadContext.put("statusDesc", HewanHttpStatus.SUCCESS.getStatusDesc());
                ThreadContext.put("hewanId", hewan.getHewanId());
                ThreadContext.put("nama", hewan.getNama());
                ThreadContext.put("jumlah", String.valueOf(hewan.getJumlah()));
            }

            if (!hewanList.isEmpty()) {
                return ResponseEntity.ok().body(hewanList);
            } else {
                BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } finally {
            Util.debugLogger.debug("{}|{}|{}|{}|{}|{}|{}", Util.getCurrentDate(), hewanReq.getId(),
                    ThreadContext.get("statusCode"), ThreadContext.get("statusDesc"),
                    ThreadContext.get("hewanId"), ThreadContext.get("nama"), ThreadContext.get("jumlah"));
            ThreadContext.clearAll();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllHewan(@RequestHeader(value = "Authorization") String authorization) {
        try {
            if (!hewanServices.checkAuthorization(authorization, String.valueOf(hewanRequestDto))) {
                BaseResponse unauthorizedResponse = new BaseResponse(HewanHttpStatus.FAILED);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(unauthorizedResponse);
            }
            List<Hewan> allHewan = hewanServices.findAllHewan();
            for (Hewan hewan : allHewan) {
                ThreadContext.put("id", String.valueOf(hewan.getId()));
                ThreadContext.put("statusCode", HewanHttpStatus.SUCCESS.getStatusCode());
                ThreadContext.put("statusDesc", HewanHttpStatus.SUCCESS.getStatusDesc());
                ThreadContext.put("hewanId", hewan.getHewanId());
                ThreadContext.put("nama", hewan.getNama());
                ThreadContext.put("jumlah", String.valueOf(hewan.getJumlah()));
            }
            if (!allHewan.isEmpty()) {
                return ResponseEntity.ok().body(allHewan);
            }
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } finally {
            Util.debugLogger.debug("{}|{}|{}|{}|{}|{}|{}", Util.getCurrentDate(), hewanReq.getId(),
                    ThreadContext.get("statusCode"), ThreadContext.get("statusDesc"),
                    ThreadContext.get("hewanId"), ThreadContext.get("nama"), ThreadContext.get("jumlah"));
            ThreadContext.clearAll();
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

                    Hewan savedHewan = hewanServices.save(hewanToUpdate);
                    ThreadContext.put("id", String.valueOf(savedHewan.getId()));
                    ThreadContext.put("statusCode", HewanHttpStatus.SUCCESS.getStatusCode());
                    ThreadContext.put("statusDesc", HewanHttpStatus.SUCCESS.getStatusDesc());
                    ThreadContext.put("hewanId", savedHewan.getHewanId());
                    ThreadContext.put("nama", savedHewan.getNama());
                    ThreadContext.put("jumlah", String.valueOf(savedHewan.getJumlah()));
                }
            }

            BaseResponse response = new BaseResponse(HewanHttpStatus.SUCCESS);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } finally {
            Util.debugLogger.debug("{}|{}|{}|{}|{}|{}|{}", Util.getCurrentDate(), hewanReq.getId(),
                    ThreadContext.get("statusCode"), ThreadContext.get("statusDesc"),
                    ThreadContext.get("hewanId"), ThreadContext.get("nama"), ThreadContext.get("jumlah"));
            ThreadContext.clearAll();
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
            for (Hewan hewan : hewanList) {
                ThreadContext.put("id", String.valueOf(hewan.getId()));
                ThreadContext.put("hewanId", hewan.getHewanId());
                ThreadContext.put("nama", hewan.getNama());
                ThreadContext.put("jumlah", String.valueOf(hewan.getJumlah()));
                Util.debugLogger.debug("{}|{}|{}|{}|{}|{}|{}", Util.getCurrentDate(), hewanReq.getId(),
                        ThreadContext.get("statusCode"), ThreadContext.get("statusDesc"),
                        ThreadContext.get("hewanId"), ThreadContext.get("nama"), ThreadContext.get("jumlah"));
                ThreadContext.clearAll();
                hewanServices.deleteByHewanId(hewanId);
            }
            BaseResponse response = new BaseResponse(HewanHttpStatus.SUCCESS);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse(HewanHttpStatus.FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } finally {
            ThreadContext.clearAll();
        }
    }
}

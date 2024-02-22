package com.example.restapi.domain.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HewanRequestDto {
    private String hewanId;
    private Map<String, HewanDto> hewan;
}

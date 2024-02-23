package com.example.restapi.domain.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
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
    @JsonProperty("hewan_id")
    @SerializedName("hewan_id")
    private String hewanId;
    private Map<String, HewanDto> hewan;
}

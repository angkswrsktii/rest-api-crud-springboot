package com.example.restapi.domain.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HewanDto {
    @JsonProperty("nama")
    @SerializedName("nama")
    private String nama;

    @JsonProperty("makanan")
    @SerializedName("makanan")
    private String makanan;

    @JsonProperty("jumlah")
    @SerializedName("jumlah")
    private int jumlah;
}

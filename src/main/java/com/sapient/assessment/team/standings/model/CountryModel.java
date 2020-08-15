package com.sapient.assessment.team.standings.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CountryModel {

    @SerializedName(value = "country_id")
    private String id;

    @SerializedName(value = "country_name")
    private String name;
}

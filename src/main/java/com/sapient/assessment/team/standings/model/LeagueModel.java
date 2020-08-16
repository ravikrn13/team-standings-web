package com.sapient.assessment.team.standings.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class LeagueModel {

    @SerializedName(value = "league_id")
    private String id;

    @SerializedName(value = "league_name")
    private String name;

    @SerializedName(value = "country_id")
    private String countryId;

    @SerializedName(value = "country_name")
    private String countryName;
}

package com.sapient.assessment.team.standings.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class StandingsModel {

    @SerializedName(value = "overall_league_position")
    private String overallPosition;

    private String countryId;

    @SerializedName(value = "country_name")
    private String countryName;

    @SerializedName(value = "team_id")
    private String teamId;

    @SerializedName(value = "team_name")
    private String teamName;

    @SerializedName(value = "league_id")
    private String leagueId;

    @SerializedName(value = "league_name")
    private String leagueName;
}

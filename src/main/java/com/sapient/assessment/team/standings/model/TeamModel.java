package com.sapient.assessment.team.standings.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TeamModel {

    @SerializedName(value = "team_id", alternate = "team_key")
    private String id;

    @SerializedName(value = "team_name")
    private String name;

    private LeagueModel league;
}

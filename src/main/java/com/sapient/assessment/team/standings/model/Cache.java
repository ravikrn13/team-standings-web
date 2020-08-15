package com.sapient.assessment.team.standings.model;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class Cache {

    private static final Set<CountryModel> countrySet = new HashSet<>();
    private static final Set<LeagueModel> leagueSet = new HashSet<>();
    private static final Set<TeamModel> teamSet = new HashSet<>();

    public Set<CountryModel> getCountries() {
        return Collections.unmodifiableSet(countrySet);
    }

    public Set<LeagueModel> getLeagues() {
        return Collections.unmodifiableSet(leagueSet);
    }

    public Set<TeamModel> getTeams() {
        return Collections.unmodifiableSet(teamSet);
    }

    public void addCountries(Set<CountryModel> pCountrySet) {
        countrySet.addAll(pCountrySet);
    }

    public void addLeagues(Set<LeagueModel> pLeagueSet) {
        leagueSet.addAll(pLeagueSet);
    }

    public void addTeams(Set<TeamModel> pTeamSet) {
        teamSet.addAll(pTeamSet);
    }

}

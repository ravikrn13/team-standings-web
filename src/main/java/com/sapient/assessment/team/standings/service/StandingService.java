package com.sapient.assessment.team.standings.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sapient.assessment.team.standings.model.Cache;
import com.sapient.assessment.team.standings.model.LeagueModel;
import com.sapient.assessment.team.standings.model.StandingsModel;
import com.sapient.assessment.team.standings.model.TeamModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class StandingService {

    @Value("${application.url.standings}")
    private String standingsUrl;

    @Value("${application.apikey}")
    private String apikey;

    @Autowired
    private Gson gson;

    @Autowired
    private Cache cache;

    @Autowired
    private RestTemplate restTemplate;

    public Set<StandingsModel> findTeamStanding(String countryId, String leagueId, String teamId) {

        Set<StandingsModel> response = null;

        if (teamId != null && !teamId.isEmpty()) {
            response = findByTeam(teamId);
        }

        if (response == null && leagueId != null && !leagueId.isEmpty()) {
            response = findByLeague(leagueId);
        }

        //error

        return response;
    }


    private Set<StandingsModel> findByLeague(String leagueId) {
        Optional<LeagueModel> leagueModelOptional = cache.getLeagues().stream().filter(e -> leagueId.equals(e.getId())).findFirst();
        if (leagueModelOptional.isPresent()) {
            LeagueModel leagueModel = leagueModelOptional.get();
            String json = makeRequest(standingsUrl + leagueModel.getId() + "&APIkey=" + apikey);
            HashSet<StandingsModel> standingsModelSet = getStandingsModel(json);
            for (StandingsModel standingsModel : standingsModelSet) {
                //standingsModel.setCountryId(leagueModel.getCountry().getId());
            }
            return standingsModelSet;
        } else
            return null;

    }

    private Set<StandingsModel> findByTeam(String teamId) {
        log.info("Finding standings by teamId {}", teamId);
        Optional<TeamModel> teamModelOptional = cache.getTeams().stream().filter(e -> teamId.equals(e.getId())).findFirst();
        if (teamModelOptional.isPresent()) {
            TeamModel team = teamModelOptional.get();
            String json = makeRequest(standingsUrl + team.getLeague().getId() + "&APIkey=" + apikey);
            HashSet<StandingsModel> standingsModels = getStandingsModel(json);
            StandingsModel result = standingsModels.stream().filter(e -> e.getTeamId().equals(teamId)).findFirst().get();
           // result.setCountryId(team.getLeague().getCountry().getId());
            HashSet<StandingsModel> set = new HashSet<>();
            set.add(result);
            return set;
        } else
            return null;

    }

    private HashSet<StandingsModel> getStandingsModel(String json) {
        Type standingsType = new TypeToken<HashSet<StandingsModel>>() {
        }.getType();
        HashSet<StandingsModel> standingsModelHashSet = gson.fromJson(json, standingsType);
        log.debug("All standings {}", standingsModelHashSet.size());
        return standingsModelHashSet;
    }

    private String makeRequest(String url) {
        log.info("Making request with url {}", url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        log.info("Received response for url {} status {}", url, response.getStatusCode());
        return response.getBody();
    }
}

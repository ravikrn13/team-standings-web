package com.sapient.assessment.team.standings.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sapient.assessment.team.standings.model.Cache;
import com.sapient.assessment.team.standings.model.CountryModel;
import com.sapient.assessment.team.standings.model.LeagueModel;
import com.sapient.assessment.team.standings.model.TeamModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.HashSet;

@Service
@Slf4j
public class CacheService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    @Autowired
    private Cache cache;

    @Value("${application.url.cache.country}")
    private String countriesUrl;

    @Value("${application.url.cache.leagues}")
    private String leaguesUrl;

    @Value("${application.url.cache.teams}")
    private String teamsUrl;

    @Value("${application.apikey}")
    private String apikey;


    public void populateCache() {
        log.info("Starting cache population");

        log.info("Fetching all the countries");
        String json = makeRequest(countriesUrl + apikey);
        log.debug(json);

        Type countryType = new TypeToken<HashSet<CountryModel>>(){}.getType();
        HashSet<CountryModel> countryModelHashSet = gson.fromJson(json, countryType);
        log.debug("All countries {}", countryModelHashSet.size());
        cache.addCountries(countryModelHashSet);

        log.info("Fetching all the leagues");
        json = makeRequest(leaguesUrl + apikey);
        log.debug(json);
        Type leaguesType = new TypeToken<HashSet<LeagueModel>>(){}.getType();
        HashSet<LeagueModel> leagueModelHashSet = gson.fromJson(json, leaguesType);
        log.debug("All leagues {}", leagueModelHashSet.size());
        cache.addLeagues(leagueModelHashSet);

        for(LeagueModel leagueModel : leagueModelHashSet) {
            log.info("Fetching teams for league {}", leagueModel.getName());
            json = makeRequest(teamsUrl+ leagueModel.getId() + "&APIkey=" + apikey);
            log.debug(json);
            Type teamsType = new TypeToken<HashSet<TeamModel>>(){}.getType();
            HashSet<TeamModel> teamModelHashSet = gson.fromJson(json, teamsType);
            log.debug("Fetched teams for league {} is {}", leagueModel.getName(), teamModelHashSet.size());

            for(TeamModel team : teamModelHashSet) {
                team.setLeague(leagueModel);
            }
            cache.addTeams(teamModelHashSet);
        }

        log.info("Cache population successful");
    }

    private String makeRequest(String url) {
        log.info("Making request with url {}", url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        log.info("Received response for url {} status {}", url, response.getStatusCode());
        return response.getBody();
    }
}

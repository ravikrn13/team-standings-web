package com.sapient.assessment.team.standings.controller;

import com.sapient.assessment.team.standings.model.Cache;
import com.sapient.assessment.team.standings.model.StandingsModel;
import com.sapient.assessment.team.standings.service.StandingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@Slf4j
public class TeamStandingsController {

    @Autowired
    private Cache cache;

    @Autowired
    private StandingService standingService;

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("countries", cache.getCountries());
        model.addAttribute("leagues", cache.getLeagues());
        model.addAttribute("teams", cache.getTeams());
        return "home";
    }

    @PostMapping("/findTeamStanding")
    public String findTeamStandings(Model model, @RequestParam String country, @RequestParam String league,
                                    @RequestParam String team) {
        log.info("Request received with countryId {}, leagueId {} and teamId {}", country, league, team);
        model.addAttribute("countries", cache.getCountries());
        model.addAttribute("leagues", cache.getLeagues());
        model.addAttribute("teams", cache.getTeams());

        if((league == null || league.isEmpty()) && (team == null || team.isEmpty())) {
            model.addAttribute("results", "Please select League or Team");
        }
        else {
            Set<StandingsModel> results = standingService.findTeamStanding(country, league, team);
            model.addAttribute("results", results);
        }

        return "home";
    }
}

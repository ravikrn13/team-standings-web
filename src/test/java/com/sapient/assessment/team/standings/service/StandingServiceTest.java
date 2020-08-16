package com.sapient.assessment.team.standings.service;

import com.sapient.assessment.team.standings.exception.StandingsException;
import com.sapient.assessment.team.standings.model.StandingsModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class StandingServiceTest {

    @Autowired
    private StandingService standingService;

    @Test
    public void testFindStandingsByTeam() {
        Set<StandingsModel> result = standingService.findTeamStanding("41", "177", "3047");
        Assertions.assertTrue(result != null);
        Assertions.assertTrue(result.size() == 1);
        Assertions.assertTrue(result.iterator().next().getTeamId().equals("3047"));
    }

    @Test
    public void testFindStandingsByLeague() {
        Set<StandingsModel> result = standingService.findTeamStanding("41", "177", "");
        Assertions.assertTrue(result != null);
        Assertions.assertTrue(result.iterator().next().getLeagueId().equals("177"));
    }

    @Test
    public void testFindStandingsByCountry() {
        try {
            Set<StandingsModel> result = standingService.findTeamStanding("41", "", "");
            Assertions.fail();
        } catch (StandingsException e) {
            // empty
        }
    }

    @Test
    public void testFindStandingsByInvalidTeam() {
        Set<StandingsModel> result = standingService.findTeamStanding("41", "177", "30437");
        Assertions.assertTrue(result != null);
        Assertions.assertTrue(result.size() > 1);
        Assertions.assertTrue(result.iterator().next().getLeagueId().equals("177"));
    }

    @Test
    public void testFindStandingsByInvalidTeamLeague() {
        try {
            Set<StandingsModel> result = standingService.findTeamStanding("41", "1707", "30437");
            Assertions.fail();
        } catch (StandingsException e) {
            // empty
        }
    }
}

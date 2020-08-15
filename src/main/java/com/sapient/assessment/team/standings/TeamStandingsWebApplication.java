package com.sapient.assessment.team.standings;

import com.sapient.assessment.team.standings.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TeamStandingsWebApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TeamStandingsWebApplication.class, args);
		ctx.getBean(CacheService.class).populateCache();
	}

}

package com.sapient.assessment.team.standings.service;

import com.sapient.assessment.team.standings.model.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CacheServiceTest {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private Cache cache;

    @Test
    public void testPopulateCache() {
        Assertions.assertFalse(cache.isCachePopulated());
        cacheService.populateCache();
        Assertions.assertTrue(cache.isCachePopulated());
    }
}

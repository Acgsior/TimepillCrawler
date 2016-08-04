package com.acgsior.tdd;

import com.acgsior.bootstrap.ICrawledDataCacheLogger;
import com.acgsior.cache.CacheManager;
import com.acgsior.factory.URLFactory;
import com.acgsior.selector.impl.person.PersonObjectSelector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by mqin on 8/3/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class IntegrationTest {

    private String pidOfJane = "100123750";
    private String pidOfMine = "100079421";

    @Resource(name = "tpURLFactory")
    private URLFactory tpURLFactory;

    @Resource(name = "personSelector")
    private PersonObjectSelector personSelector;

    @Resource(name = "cacheManager")
    private CacheManager cacheManager;

    @Test
    public void peopleInfoSelectTest() throws IOException {
        String personURL = tpURLFactory.getURL(URLFactory.PERSON, pidOfJane).get();
        Document document = Jsoup.connect(personURL).get();

        personSelector.select(document, Optional.of(pidOfJane));

        ((ICrawledDataCacheLogger) cacheManager.getCache()).logCacheStatus();
    }
}

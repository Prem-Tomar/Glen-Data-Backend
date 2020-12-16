package com.glen.assignment.common.config;

import com.glen.assignment.common.utils.converters.WebScrapperEventToEventsConverter;
import com.glen.assignment.common.utils.helpers.WebsiteScraperHelper;
import com.glen.assignment.services.ScraperService;
import com.glen.assignment.services.ScraperServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SystemConfigurations implements WebMvcConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScraperServiceImpl.class);
    private final String firstPageUrl = "https://www.techmeme.com/events";
    private final String secondPageUrl = "https://www.computerworld.com/article/3313417/tech-event-calendar-2020-upcoming-shows-conferences-and-it-expos.html";
    private final int timeout = 1000000000;
    @Autowired
    private ScraperService service;

    @Autowired
    private WebsiteScraperHelper websiteScraperHelper;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new WebScrapperEventToEventsConverter());
    }

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("GlenThread-");
        executor.initialize();
        return executor;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) throws ExecutionException, InterruptedException, IOException {
        scrapeUrl();
    }

    private void scrapeUrl() throws ExecutionException, InterruptedException, IOException {
        List<String> searchTags = Arrays.asList("div[class=rhov]");
        List<String> searchTags2 = Arrays.asList("tr");
        List<WebsiteScraperHelper.Events> events;
        List<WebsiteScraperHelper.Events> events2;
        websiteScraperHelper.setPageUrl(firstPageUrl);
        websiteScraperHelper.setSecondUrl(secondPageUrl);
        websiteScraperHelper.setPageParseTimeoutMillis(timeout);
        websiteScraperHelper.setSearchTags(searchTags);
        websiteScraperHelper.setSecondSerachTag(searchTags2);
        CompletableFuture<List<WebsiteScraperHelper.Events>> firstFuture = websiteScraperHelper.fetchMetaDetailsFromFirstPage();
        CompletableFuture<List<WebsiteScraperHelper.Events>> secondFuture = websiteScraperHelper.fetchMetaDetailsFromSecondPage();
        CompletableFuture<List<WebsiteScraperHelper.Events>> finalFuture = CompletableFuture.allOf(firstFuture, secondFuture).thenApplyAsync(future -> {
            List<WebsiteScraperHelper.Events> finalList = null;
            try {
                finalList = firstFuture.get();
                finalList.addAll(secondFuture.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            return finalList;
        });
        events = finalFuture.get();
        service.saveEvents(events);
    }


}

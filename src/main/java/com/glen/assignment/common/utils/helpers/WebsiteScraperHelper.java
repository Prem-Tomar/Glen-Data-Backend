package com.glen.assignment.common.utils.helpers;

import com.glen.assignment.services.ScraperServiceImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Getter
@Setter
@Component
public class WebsiteScraperHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScraperServiceImpl.class);
    private String pageUrl, secondUrl;
    private Integer pageParseTimeoutMillis;
    private List<String> searchTags, secondSerachTag;

    @Getter
    @Setter
    @ToString
    public class Events {
        String title;
        String websiteTitle;
        String time;
        String venue;
    }

//    public WebsiteScraperHelper(String pageUrl, String secondUrl, Integer pageParseTimeoutMillis,
//                                List<String> searchTags, List<String> secondSerachTag) {
//        super();
//        this.pageUrl = pageUrl;
//        this.secondUrl = secondUrl;
//        this.pageParseTimeoutMillis = pageParseTimeoutMillis;
//        this.searchTags = searchTags;
//        this.secondSerachTag = secondSerachTag;
//    }

    /**
     * This method uses main page url supplied in constructor and retrieves all the links from that page
     * which are coming under the tags expression supplied as links search tags and then fetches all the meta details for those pages
     *
     * @return : returns a list of all articles with the details fetched using the links search tag supplied in constructor.
     */
    @Async("taskExecutor")
    public CompletableFuture<List<Events>> fetchMetaDetailsFromFirstPage() throws IOException {
        LOGGER.info("Fetching first website events");
        return getAllEvents();

    }

    @Async("taskExecutor")
    public CompletableFuture<List<Events>> fetchMetaDetailsFromSecondPage() throws IOException {
        LOGGER.info("Fetching second website events");
        return getAllEventsForSecondPage();

    }

    /**
     * Get events from first website
     *
     * @return
     * @throws IOException
     */
    @Async("taskExecutor")
    private CompletableFuture<List<Events>> getAllEvents() throws IOException {
        List<Events> links = new ArrayList<>();
        Document doc = Jsoup.parse(new URL(pageUrl), pageParseTimeoutMillis);
        searchTags.forEach(tag -> {
            Elements elems = doc.select(tag);
            elems.forEach(e -> {
                Element[] ele = e.select("a[href]").select("div").toArray(new Element[0]);
                if (ele.length > 2) {
                    Events events = new Events();
                    events.setTime(ele[0].text());
                    events.setTitle(ele[1].text());
                    events.setVenue(ele[2].text());
                    events.setWebsiteTitle(pageUrl);
                    links.add(events);
                }

            });
        });
        return CompletableFuture.completedFuture(links);
    }

    /**
     * Get all events from second website url
     *
     * @return
     * @throws IOException
     */
    @Async("taskExecutor")
    private CompletableFuture<List<Events>> getAllEventsForSecondPage() throws IOException {
        List<Events> links = new ArrayList<>();
        Document doc = Jsoup.parse(new URL(secondUrl), pageParseTimeoutMillis);
        Elements root = doc.select("tbody");
        secondSerachTag.forEach(tag -> {
            Elements elems = root.select("tr");
            elems.forEach(e -> {
                Element[] ele = e.select("td").toArray(new Element[0]);
                if (ele.length > 3) {
                    Events events = new Events();
                    events.setTime(ele[1].text() + "-" + ele[1].text());
                    events.setTitle(e.select("th").select("a").text());
                    events.setVenue(ele[3].text());
                    events.setWebsiteTitle(secondUrl);
                    links.add(events);
                }

            });
        });
        return CompletableFuture.completedFuture(links);
    }


}

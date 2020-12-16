package com.glen.assignment.common.utils.converters;

import com.glen.assignment.common.utils.helpers.WebsiteScraperHelper;
import com.glen.assignment.services.persistence.entities.Events;
import org.springframework.core.convert.converter.Converter;

public class WebScrapperEventToEventsConverter implements Converter<WebsiteScraperHelper.Events, Events> {
    @Override
    public Events convert(WebsiteScraperHelper.Events source) {
        Events event = new Events();
        event.setTitle(source.getTitle());
        event.setDate(source.getTime());
        event.setVenue(source.getVenue());
        event.setWebsiteTitle(source.getWebsiteTitle());
        return event;
    }
}

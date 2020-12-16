package com.glen.assignment.common.utils.helpers;

import com.glen.assignment.services.persistence.entities.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceHelper {
    @Autowired
    ConversionService conversionService;

    public List<Events> getConvertedEventsOfWebsite(List<WebsiteScraperHelper.Events> events) {

        return (List<Events>) conversionService.convert(events, TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(WebsiteScraperHelper.Events.class)),TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(Events.class)));
    }
}

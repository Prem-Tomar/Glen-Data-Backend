package com.glen.assignment.services;

import com.glen.assignment.common.utils.Constants;
import com.glen.assignment.common.utils.FilterQuery;
import com.glen.assignment.common.utils.helpers.GenericResponse;
import com.glen.assignment.common.utils.helpers.ResponsePaginationWrapper;
import com.glen.assignment.common.utils.helpers.ServiceHelper;
import com.glen.assignment.common.utils.helpers.WebsiteScraperHelper;
import com.glen.assignment.common.utils.properties.EventProperties;
import com.glen.assignment.dto.EventRequestDTO;
import com.glen.assignment.services.persistence.dao.EventRepository;
import com.glen.assignment.services.persistence.entities.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ScraperServiceImpl implements ScraperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScraperServiceImpl.class);
    @Autowired
    EventRepository eventRepository;

    @Autowired
    ServiceHelper serviceHelper;

    @Autowired
    EventProperties eventProperties;

    /**
     * To get all events by given filter
     * @param eventRequestDTO Holds filters
     * @param page request page
     * @param limit results per page
     * @return {@link GenericResponse<ResponsePaginationWrapper,Boolean>}
     */
    @Override
    @Async("taskExecutor")
    public CompletableFuture<GenericResponse<ResponsePaginationWrapper, Boolean>> getEvents(EventRequestDTO eventRequestDTO, int page, int limit) {
        FilterQuery query = getFilterQuery(eventRequestDTO);
        ResponsePaginationWrapper<Events> wrapper = eventRepository.findEventsWithQuery(query, page, limit);
        GenericResponse<ResponsePaginationWrapper,Boolean> genericResponse = new GenericResponse<>(wrapper, false, Constants.SUCCESS, eventProperties.getMessage(), eventProperties.getDescription());
        LOGGER.info("Fetching results");
        return CompletableFuture.completedFuture(genericResponse);
    }

    /**
     * Filter query builder
     * @param eventRequestDTO
     * @return {@link FilterQuery}
     */
    private FilterQuery getFilterQuery(EventRequestDTO eventRequestDTO) {
        FilterQuery query = new FilterQuery();
        query.setTitle(eventRequestDTO.getTitle());
        query.setVenue(eventRequestDTO.getVenue());
        query.setWebsite(eventRequestDTO.getWebsite());
        return query;
    }

    /**
     * To save all incoming events to the database
     * @param events
     */
    @Override
    @Async("taskExecutor")
    public CompletableFuture<List<Events>> saveEvents(List< WebsiteScraperHelper.Events > events) {
        List<Events> eventsList = Collections.emptyList();
        if(events!=null)
             eventsList = serviceHelper.getConvertedEventsOfWebsite(events);
        List<Events> savedEvents = eventRepository.saveAll(eventsList);
        LOGGER.info("Saving events");
        return CompletableFuture.completedFuture(savedEvents);
    }
}

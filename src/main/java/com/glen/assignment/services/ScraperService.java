package com.glen.assignment.services;

import com.glen.assignment.common.utils.helpers.GenericResponse;
import com.glen.assignment.common.utils.helpers.ResponsePaginationWrapper;
import com.glen.assignment.common.utils.helpers.WebsiteScraperHelper;
import com.glen.assignment.dto.EventRequestDTO;
import com.glen.assignment.services.persistence.entities.Events;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ScraperService {
    CompletableFuture<GenericResponse<ResponsePaginationWrapper, Boolean>> getEvents(EventRequestDTO eventRequestDTO, int page, int limit);
    CompletableFuture<List<Events>> saveEvents(List< WebsiteScraperHelper.Events > events);
}

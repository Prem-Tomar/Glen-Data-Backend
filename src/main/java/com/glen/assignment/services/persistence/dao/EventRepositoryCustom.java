package com.glen.assignment.services.persistence.dao;

import com.glen.assignment.common.utils.FilterQuery;
import com.glen.assignment.common.utils.helpers.ResponsePaginationWrapper;

public interface EventRepositoryCustom {
    ResponsePaginationWrapper findEventsWithQuery(FilterQuery query, int page, int limit);
}

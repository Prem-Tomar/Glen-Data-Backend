package com.glen.assignment.services.persistence.dao;

import com.glen.assignment.common.exceptions.NoResultsException;
import com.glen.assignment.common.utils.FilterQuery;
import com.glen.assignment.common.utils.helpers.ResponsePaginationWrapper;
import com.glen.assignment.services.persistence.entities.Events;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    public EventRepositoryImpl() {
    }

    /**
     * Helps retrieval of event by given filter
     * @param filterQuery
     * @param page
     * @param limit
     * @return returns paginated results for events
     */
    @Override
    public ResponsePaginationWrapper findEventsWithQuery(FilterQuery filterQuery, int page, int limit) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Events> query = cb.createQuery(Events.class);
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(Events.class)));
        Root<Events> event = query.from(Events.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filterQuery.getTitle() != null)
            predicates.add(cb.like(event.get("title"), "%" + filterQuery.getTitle() + "%"));

        if (filterQuery.getVenue() != null)
            predicates.add(cb.like(event.get("venue"), "%" + filterQuery.getVenue() + "%"));

        if (filterQuery.getWebsite() != null) {
            predicates.add(cb.like(event.get("websiteTitle"), "%" + filterQuery.getWebsite() + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));
        cq.where(predicates.toArray(new Predicate[0]));
        ResponsePaginationWrapper wrapper = new ResponsePaginationWrapper();

        try {
            wrapper.setData(em.createQuery(query).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList());
            wrapper.setPage(page);
            wrapper.setTotal(em.createQuery(cq).getSingleResult());
        } catch (NoResultsException ex) {
            throw new NoResultsException("No results found", "No results found for given filter", Collections.emptyList(), 204);
        }
        return wrapper;
    }
}

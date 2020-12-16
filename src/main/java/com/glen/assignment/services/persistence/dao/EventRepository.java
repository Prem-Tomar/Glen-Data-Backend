package com.glen.assignment.services.persistence.dao;

import com.glen.assignment.services.persistence.entities.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Events, Long>, EventRepositoryCustom {
}

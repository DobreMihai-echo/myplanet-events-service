package com.event.event.repository;

import com.event.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByDateBeforeAndCompletedIsFalse(LocalDateTime date);
    List<Event> findAllByCompletedIsFalse();
}

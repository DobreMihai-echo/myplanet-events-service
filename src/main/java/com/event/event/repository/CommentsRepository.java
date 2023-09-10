package com.event.event.repository;

import com.event.event.domain.Comment;
import com.event.event.domain.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEvent(Event event, Pageable pageable);
}

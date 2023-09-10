package com.event.event.service;

import com.event.event.domain.Event;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EventService {
    List<Event> saveEvent(Event eventToInsert);
    List<Event> getEvents();

    List<Event> deleteEvent(Long eventId);

    List<Event> updateEvent(Long eventId, Event update);
    List<String> joinEvent(Long eventId,String usernameToJoin);
    List<String> unjoinEvent(Long eventId,String usernameToJoin);
    void markEventsAsCompleted();
}

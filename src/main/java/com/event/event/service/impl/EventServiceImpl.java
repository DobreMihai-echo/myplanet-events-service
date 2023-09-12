package com.event.event.service.impl;

import com.event.event.domain.Event;
import com.event.event.repository.EventRepository;
import com.event.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository repository;

    public List<Event> saveEvent(Event events) {
        events.setEventJoiners(new ArrayList<>());
        events.setCompleted(false);
        this.repository.save(events);
        return repository.findAllByCompletedIsFalse();
    }

    public List<Event> getEvents() {
        return this.repository.findAll();
    }

    @Override
    public List<Event> deleteEvent(Long eventId) {
        repository.deleteById(eventId);
        return repository.findAllByCompletedIsFalse();
    }

    @Override
    public List<Event> updateEvent(Long eventId, Event update) {
        Event eventToUpdate = repository.findById(eventId).get();
        eventToUpdate.setDate(update.getDate());
        eventToUpdate.setDescription(update.getDescription());
        eventToUpdate.setType(update.getType());
        repository.save(eventToUpdate);
        return repository.findAllByCompletedIsFalse();
    }

    public List<String> joinEvent(Long eventId,String username) {
        Event events = repository.findById(eventId).get();
        events.getEventJoiners().add(username);
        repository.save(events);
        return events.getEventJoiners();
    }

    public List<String> unjoinEvent(Long eventId, String username) {
        Event events = repository.getReferenceById(eventId);
        System.out.println("USERNAME" + username);
        events.getEventJoiners().remove(username);
        repository.save(events);
        System.out.println("EVENT JOINER:" + events.getEventJoiners());
        return events.getEventJoiners();
    }

    public void markEventsAsCompleted() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Event> expiredEvents = repository.findByDateBeforeAndCompletedIsFalse(currentDate);
        for (Event event: expiredEvents) {
            System.out.println("EVENT EXPIRED:" +event.getDescription());
            event.setCompleted(true);
            repository.save(event);
        }
    }

//    @Override
//    public List<FollowingEvents> getCompletedFollowerListEvents(List<String> followingUsernames) {
//
//        List<FollowingEvents> followingEvents = new ArrayList<>();
//        for (String username: followingUsernames) {
//            List<Events> completedEventsOfUser = repository.findAllByCompletedIsTrueAndEventJoinersIn(username);
//            FollowingEvents following = FollowingEvents.builder()
//                    .event(completedEventsOfUser)
//                    .username(username)
//                    .build();
//            followingEvents.add(following);
//        }
//
//        return followingEvents;
//    }
}

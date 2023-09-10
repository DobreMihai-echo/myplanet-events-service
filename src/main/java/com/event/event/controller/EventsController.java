package com.event.event.controller;

import com.event.event.domain.Comment;
import com.event.event.domain.Event;
import com.event.event.service.CommentService;
import com.event.event.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Slf4j
@CrossOrigin
public class EventsController {
    @Autowired
    private EventService service;

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<?> saveEvent(@RequestBody Event events) {
        return ResponseEntity.ok(service.saveEvent(events));
    }

    @DeleteMapping("{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable(name = "eventId") Long eventId) {
        return ResponseEntity.ok(service.deleteEvent(eventId));
    }

    @PutMapping("{eventId}")
    public ResponseEntity<?> updateEvent(@PathVariable(name = "eventId") Long eventId, @RequestBody Event events) {
        return ResponseEntity.ok(service.updateEvent(eventId,events));
    }

    @PutMapping("/join")
    public ResponseEntity<?> joinEvent(@RequestParam(name = "eventId") Long eventId, @RequestParam(name = "username") String username) {
        return ResponseEntity.ok(service.joinEvent(eventId,username));
    }

    @DeleteMapping("/unjoin")
    public ResponseEntity<?> unjoinEvent(@RequestParam(name = "eventId") Long eventId, @RequestParam(name = "username") String username) {
        return ResponseEntity.ok(service.unjoinEvent(eventId,username));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getEvents() {
        return ResponseEntity.ok(service.getEvents());
    }

    @GetMapping("{eventId}/messages")
    public ResponseEntity<?> getEventComments(@PathVariable("eventId") Long eventId,
                                              @RequestParam("page") Integer page,
                                              @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page-1;
        size = size <= 0 ? 5 : size;
        List<Comment> comments = commentService.getPostMessagePaginate(eventId, page, size);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("{eventId}/messages/create")
    public ResponseEntity<?> createPostComment(@PathVariable("eventId") Long eventId,
                                               @RequestParam(value = "content") String content,
                                               @RequestParam(value = "username") String author) {
        return new ResponseEntity<>(commentService.createNewMessage(content,eventId,author), HttpStatus.OK);
    }

    @PostMapping("{eventId}/messages/{messsageId}/update")
    public ResponseEntity<?> updatePostComment(@PathVariable("messageId") Long messageId,
                                               @RequestParam(value = "content") String content) {
        Comment commentToUpdate = commentService.updateMessage(messageId, content);
        return new ResponseEntity<>(commentToUpdate, HttpStatus.OK);
    }

    @PostMapping("/event/message/{messageId}/delete")
    public ResponseEntity<?> deletePostComment(@PathVariable("messageId") Long messageId) {
        commentService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

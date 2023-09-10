package com.event.event.service.impl;

import com.event.event.domain.Comment;
import com.event.event.domain.Event;
import com.event.event.repository.CommentsRepository;
import com.event.event.repository.EventRepository;
import com.event.event.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentsRepository repository;

    @Autowired
    private EventRepository eventRepository;



    @Override
    public Comment getMessageById(Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Comment createNewMessage(String message, Long eventId, String author) {
        Comment messageToCreate = new Comment();
        Event event = eventRepository.findById(eventId).orElseThrow(RuntimeException::new);
        messageToCreate.setContent(message);
        messageToCreate.setAuthor(author);
        messageToCreate.setEvent(event);
        messageToCreate.setDateCreated(LocalDateTime.now());
        messageToCreate.setLastModified(LocalDateTime.now());
        return repository.save(messageToCreate);
    }

    @Override
    public Comment updateMessage(Long messageId, String message) {
        Comment messageToUpdate = repository.findById(messageId).orElseThrow(RuntimeException::new);
        messageToUpdate.setContent(message);
        messageToUpdate.setLastModified(LocalDateTime.now());
        return repository.save(messageToUpdate);
    }

    @Override
    public Comment createEventMessage(Long eventId, String content, String username) {

        Event targetEvent = eventRepository.findById(eventId).orElseThrow(RuntimeException::new);
        Comment message = this.createNewMessage(content,eventId,username);
        targetEvent.setCommentCount(targetEvent.getCommentCount()+1);
        eventRepository.save(targetEvent);
        return message;
    }

    @Override
    public Comment updateEventMessage(Long messageId, String message) {
        return updateMessage(messageId,message);
    }

    @Override
    public void deleteEventMessage(Long messageId, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(RuntimeException::new);
        this.deleteMessage(messageId);
        event.setCommentCount(event.getCommentCount() - 1);
        eventRepository.save(event);
    }

    @Override
    public void deleteMessage(Long messageId) {
        repository.deleteById(messageId);
    }

    @Override
    public List<Comment> getPostMessagePaginate(Long eventId, Integer page, Integer size) {
        Event event = eventRepository.findById(eventId).orElseThrow(RuntimeException::new);
        List<Comment> messages = repository.findByEvent(event, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreated")));
        return messages;
    }
}

package com.event.event.service;

import com.event.event.domain.Comment;

import java.util.List;

public interface CommentService {

    Comment getMessageById(Long id);
    Comment createNewMessage(String message, Long eventId, String author);
    Comment updateMessage(Long messageId, String message);

    Comment createEventMessage(Long eventId, String content, String username);

    Comment updateEventMessage(Long messageId, String message);

    void deleteEventMessage(Long messageId, Long eventId);
    void deleteMessage(Long messageId);

    public List<Comment> getPostMessagePaginate(Long eventId, Integer page, Integer size);
}

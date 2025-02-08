package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message post(Message newMessage) {

        // check message is not blank
        if (newMessage.getMessageText().isBlank() || newMessage.getMessageText() == null) {
            throw new IllegalArgumentException("Message cannot be blank");
        }

        // check message is not over 255 char
        if (newMessage.getMessageText().length() > 255 || newMessage.getMessageText() == null) {
            throw new IllegalArgumentException("Message cannot exceed 255 characters");
        }

        // check message is posted by a valid user
        if (messageRepository.findById(newMessage.getPostedBy()) != null) {
            throw new IllegalArgumentException("Message must be posted by an existing user");
        }

        return messageRepository.save(newMessage);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}

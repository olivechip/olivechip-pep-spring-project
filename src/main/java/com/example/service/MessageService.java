package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message post(Message newMessage) {

        // check message is not blank
        if (newMessage.getMessageText().isBlank()) {
            throw new IllegalArgumentException("Message cannot be blank");
        }

        // check message is not over 255 char
        if (newMessage.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message cannot exceed 255 characters");
        }

        // check message is posted by a valid user
        Optional<Message> existingUser = messageRepository.findById(newMessage.getPostedBy());
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("Message must be posted by an existing user");
        }

        Message savedMessage = messageRepository.save(newMessage);
        return savedMessage;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        // if no message, return null
        if (optionalMessage.isEmpty()) {
            return null;
        }
        return optionalMessage.get();
    }

    public int deleteMessageById(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    // FIX EMPTY STRING BEING PASSED THROUGH AS A VALID STRING
    public int patchMessageById(int messageId, String messageText) {

        // check that message exists
        if (messageRepository.existsById(messageId)) {

            // check that messageText is not blank or over 255 characters
            if (messageText == null || messageText.isBlank()) {
                throw new IllegalArgumentException("Message cannot be empty or null");
            }
            if (messageText.length() > 255) {
                throw new IllegalArgumentException("Message cannot exceed 255 characters");
            }

            // update message
            Message message = messageRepository.findById(messageId).get();
            message.setMessageText(messageText);
            messageRepository.save(message);
            
            return 1;
        }
        return 0;
    }

    public List<Message> getAllMessagesByAccountId(int accountId) {
        return messageRepository.getAllMessagesByAccountId(accountId);
    }
}

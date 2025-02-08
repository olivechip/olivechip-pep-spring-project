package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */

@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account newUser) {
        try {
            Account registeredUser = accountService.register(newUser);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Username already exists")) {
                // duplicate username
                return ResponseEntity.status(409).build();
            } else {
                // invalid username or password
                return ResponseEntity.status(400).build();
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account user) {
        try {
            Account loggedInUser = accountService.login(user);
            return ResponseEntity.ok(loggedInUser);
        } catch (IllegalArgumentException e) {
            // invalid credentials
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> post(@RequestBody Message newMessage) {
        try {
            Message postedMessage = messageService.post(newMessage);
            return ResponseEntity.ok(postedMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        try {
            List<Message> messages = messageService.getAllMessages();
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        try {
            Message message = messageService.getMessageById(messageId);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable int messageId) {
        try {
            Integer rowsDeleted = messageService.deleteMessageById(messageId);
            if (rowsDeleted == 1) {
                return ResponseEntity.ok(rowsDeleted);
            }
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    // FIX EMPTY STRING BEING PASSED THROUGH AS A VALID STRING
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> patchMessageById(@PathVariable int messageId, @RequestBody String messageText) {
        try {
            System.err.println("messageText in controller: " + messageText);
            Integer rowsAffected = messageService.patchMessageById(messageId, messageText);
            if (rowsAffected == 1) {
                return ResponseEntity.ok(rowsAffected);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int accountId) {
        try {
            List<Message> messages = messageService.getAllMessagesByAccountId(accountId);
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

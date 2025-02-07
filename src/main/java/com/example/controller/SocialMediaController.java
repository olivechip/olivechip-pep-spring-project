package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

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

    // @Autowired
    // private MessageService messageService;

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

    // // should post message
    // @PostMapping("/messages")
    // public Message post(@RequestBody Message newMessage) {

    // }

    // // should get all messages
    // @GetMapping("/messages")
    // public Message post(@RequestBody Message newMessage) {

    // }

    // // should get message by its id
    // @GetMapping("/messages/{messageId}")
    // public Message post(@RequestBody Message newMessage) {

    // }

}

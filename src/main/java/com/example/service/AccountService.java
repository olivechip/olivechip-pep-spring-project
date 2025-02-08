package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account newUser) {

        // username and password validation
        if (newUser.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        if (newUser.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }

        // duplicate validation
        if (accountRepository.findByUsername(newUser.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        return accountRepository.save(newUser);
    }

    public Account login(Account user) {

        // username and password validation
        if (user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        if (user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }

        // check for existing account by username
        Account existingAccount = accountRepository.findByUsername(user.getUsername());
        if (existingAccount == null) {
            throw new IllegalArgumentException("Username not found");
        }

        // check that input password matches database password
        if (!existingAccount.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        }

        return existingAccount;
    }
}

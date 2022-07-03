package com.palestiner.insidedemo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.palestiner.insidedemo.model.Message;
import com.palestiner.insidedemo.model.User;
import com.palestiner.insidedemo.model.view.Views;
import com.palestiner.insidedemo.service.MessageService;
import com.palestiner.insidedemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @PostMapping("/messages")
    @JsonView(Views.Message.class)
    public ResponseEntity<String> createMessage(@RequestBody Message message) {
        User userDb = userService.findUserByUsername(message.getUser().getUsername());
        message.setUser(userDb);
        messageService.saveMessage(message);
        return new ResponseEntity<>("Message was created", HttpStatus.CREATED);
    }

    @GetMapping("/messages")
    @JsonView(Views.Message.class)
    public ResponseEntity<List<Message>> getLastMessagesByUser(@RequestBody Message message) {
        List<Message> lastMessages = messageService.getLastMessages(message);
        return ResponseEntity.ok(lastMessages);
    }
}

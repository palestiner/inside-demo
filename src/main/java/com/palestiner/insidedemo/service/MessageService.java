package com.palestiner.insidedemo.service;

import com.palestiner.insidedemo.model.Message;
import com.palestiner.insidedemo.repo.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    @Transactional
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getLastMessages(Message message) throws NumberFormatException {
        int limit = Integer.parseInt(message.getContent().split(" ")[1]);
        return messageRepository.getLastMessages(message.getUser().getUsername(), limit);
    }
}

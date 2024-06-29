package com.anurag.service;

import com.anurag.model.Chat;
import com.anurag.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRepository chatRepository;
    @Override
    public Chat createchat(Chat chat) {
        return chatRepository.save(chat);

    }
}

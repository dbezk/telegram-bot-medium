package com.dimbiy.bot.service;

import com.dimbiy.bot.entity.Message;
import com.dimbiy.bot.entity.TelegramUser;
import com.dimbiy.bot.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message save(String text, TelegramUser user) {
        Message message = new Message();
        message.setText(text);
        message.setUser(user);
        message.setSendTime(LocalDateTime.now());
        return messageRepository.save(message);
    }

}

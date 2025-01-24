package com.dimbiy.bot.service;

import com.dimbiy.bot.entity.TelegramUser;
import com.dimbiy.bot.repository.TelegramUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;

    public TelegramUser saveOrUpdate(
            Long telegramUserId,
            String username,
            String firstName,
            String lastName,
            Long chatId
    ) {
        TelegramUser telegramUser = telegramUserRepository.findByTelegramUserId(telegramUserId)
                .orElse(null);
        if(telegramUser == null) {
            telegramUser = new TelegramUser();
            telegramUser.setTelegramUserId(telegramUserId);
            telegramUser.setUsername(username);
            telegramUser.setFirstName(firstName);
            telegramUser.setLastName(lastName);
            telegramUser.setChatId(chatId);
            telegramUser.setCreatedAt(LocalDateTime.now());
        }
        telegramUser.setUpdatedAt(LocalDateTime.now());
        return telegramUserRepository.save(telegramUser);
    }

}

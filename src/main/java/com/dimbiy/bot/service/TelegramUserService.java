package com.dimbiy.bot.service;

import com.dimbiy.bot.entity.Message;
import com.dimbiy.bot.entity.TelegramUser;
import com.dimbiy.bot.repository.TelegramUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;
    private final MessageService messageService;

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
            telegramUser.setSubscribed(true);
        }
        telegramUser.setUpdatedAt(LocalDateTime.now());
        return telegramUserRepository.save(telegramUser);
    }

    public void changeSubState(Long telegramUserId) {
        telegramUserRepository.findByTelegramUserId(telegramUserId).ifPresentOrElse(
                telegramUser -> {
                    telegramUser.setSubscribed(!telegramUser.isSubscribed());
                    telegramUserRepository.save(telegramUser);
                },
                () -> log.error("Error while changing the sub/unsub state. User with id {}", telegramUserId)
        );
    }

    @Transactional
    public TelegramUser getTelegramUser(Long telegramUserId) {
        TelegramUser user = telegramUserRepository.findByTelegramUserId(telegramUserId).orElse(null);
        if (user != null) {
            user.getMessages().size();
        }
        return user;
    }

    @Transactional
    public void addMessageToUser(Update update) {
        Long telegramUserId = update.getMessage().getFrom().getId();
        String messageText = update.getMessage().getText();

        telegramUserRepository.findByTelegramUserId(telegramUserId).ifPresentOrElse(
                telegramUser -> {
                    Message newMessage = messageService.save(messageText, telegramUser);
                    if (newMessage != null) {
                        telegramUser.getMessages().add(newMessage);
                        telegramUserRepository.save(telegramUser);
                    } else {
                        log.error("Error while adding a new message to user. Message is null!");
                    }
                },
                () -> log.error("Error while adding a new message to user. User with id {} is not found!", telegramUserId)
        );
    }

}

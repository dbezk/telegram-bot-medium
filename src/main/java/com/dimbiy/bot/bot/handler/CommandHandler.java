package com.dimbiy.bot.bot.handler;

import com.dimbiy.bot.bot.LessonsBot;
import com.dimbiy.bot.entity.TelegramUser;
import com.dimbiy.bot.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class CommandHandler {

    private final TelegramUserService telegramUserService;

    public void handleCommand(Message message, LessonsBot bot) {
        String command = message.getText();
        if(command.equals("/start")) {
            TelegramUser telegramUser = telegramUserService.saveOrUpdate(
                    message.getFrom().getId(),
                    message.getFrom().getUserName(),
                    message.getFrom().getFirstName(),
                    message.getFrom().getLastName(),
                    message.getChatId()
            );
            bot.sendMessage("Created at: " + telegramUser.getCreatedAt() +
                    ", Updated at" + telegramUser.getUpdatedAt(),
                    message.getChatId());
        }
    }

}

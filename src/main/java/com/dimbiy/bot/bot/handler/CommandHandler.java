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
            bot.sendMessage("Hello," + message.getFrom().getFirstName(),
                    message.getChatId());
        }
        if(command.equals("/subscribe")) {
            telegramUserService.changeSubState(message.getFrom().getId());
            bot.sendMessage("Your sub/unsub state is changed! Use /data to check.",
                    message.getChatId());
        }
        if(command.equals("/data")) {
            TelegramUser telegramUser = telegramUserService.getTelegramUser(message.getFrom().getId());
            bot.sendMessage("Your telegram user id: " + telegramUser.getTelegramUserId() +
                    "\nYour last update at: " + telegramUser.getUpdatedAt() +
                    "\nSub/unsub state: " + (telegramUser.isSubscribed() ? "active sub" : "inactive sub") +
                    "\nMessages amount: " + telegramUser.getMessages().size(),
                    message.getChatId());
        }
    }

}

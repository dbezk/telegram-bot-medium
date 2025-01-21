package com.dimbiy.bot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class LessonsBot extends TelegramLongPollingBot {

    @Value(value = "${bot.username}")
    private String botUsername;

    public LessonsBot(@Value(value = "${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            sendMessage(message, update.getMessage().getChatId());
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    public void sendMessage(String message, Long chatId) {
        SendMessage newMessage = new SendMessage();
        newMessage.setChatId(chatId);
        newMessage.setText(message);
        try {
            execute(newMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}

package com.dimbiy.bot.bot;

import com.dimbiy.bot.bot.handler.CommandHandler;
import com.dimbiy.bot.bot.handler.TextHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class LessonsBot extends TelegramLongPollingBot {

    private final TextHandler textHandler;
    private final CommandHandler commandHandler;

    @Value(value = "${bot.username}")
    private String botUsername;

    public LessonsBot(@Value(value = "${bot.token}") String botToken,
                      TextHandler textHandler,
                      CommandHandler commandHandler) {
        super(botToken);
        this.textHandler = textHandler;
        this.commandHandler = commandHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if(message.isCommand()) {
            commandHandler.handleCommand(message, this);
        }
        if(message.hasText() && !message.isCommand()) {
            textHandler.handleText(message, this);
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

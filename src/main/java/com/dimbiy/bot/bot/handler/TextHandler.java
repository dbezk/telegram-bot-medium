package com.dimbiy.bot.bot.handler;

import com.dimbiy.bot.bot.LessonsBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class TextHandler {

    public void handleText(Message message, LessonsBot bot) {
        String text = message.getText();
        bot.sendMessage("Hello from Text handler. You wrote: " + text,
                message.getChatId());
    }

}

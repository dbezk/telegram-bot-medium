package com.dimbiy.bot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "telegram_users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TelegramUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long telegramUserId;
    private String username;
    private String firstName;
    private String lastName;
    private Long chatId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

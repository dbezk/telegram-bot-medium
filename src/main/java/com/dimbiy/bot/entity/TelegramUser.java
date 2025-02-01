package com.dimbiy.bot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private boolean subscribed;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

}

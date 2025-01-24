package com.dimbiy.bot.repository;

import com.dimbiy.bot.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
    Optional<TelegramUser> findByTelegramUserId(Long telegramUserId);
}

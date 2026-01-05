package ru.job4j.bmb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${telegram.bot.name}")
    private String telegramBotName;

    public void print() {
        System.out.println("Telegram Bot Name: " + telegramBotName);
    }
}

package ru.job4j.bmb.content;

import lombok.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Content {
    private final Long chatId;
    private String text;
    private InputFile photo;
    private InlineKeyboardMarkup markup;
    private InputFile audio;
    private ReplyKeyboardMarkup replyMarkup;

    public Content(Long chatId) {
        this.chatId = chatId;
    }

}

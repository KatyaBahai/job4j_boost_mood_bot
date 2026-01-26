package ru.job4j.bmb.telegramapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.job4j.bmb.repository.MoodRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TgUI {
    private final MoodRepository moodRepository;

    public TgUI(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    public InlineKeyboardMarkup buildButtons() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (var mood : moodRepository.findAll()) {
            keyboard.add(List.of(createBtn(mood.getText(), mood.getId())));
        }
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardButton createBtn(String name, Long moodId) {
        var inline = new InlineKeyboardButton();
        inline.setText(name);
        inline.setCallbackData(String.valueOf(moodId));
        return inline;
    }

    public ReplyKeyboardMarkup mainMenu() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("choose you mood"));
        row1.add(new KeyboardButton("week mood log"));
        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("month mood log"));
        row2.add(new KeyboardButton("awards"));
        keyboard.add(row2);

        markup.setKeyboard(keyboard);
        markup.setResizeKeyboard(true);
        markup.setSelective(true);

        return markup;
    }
}

package ru.job4j.bmb.telegramapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.job4j.bmb.model.Mood;
import ru.job4j.bmb.repository.MoodRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {TgUI.class})
class TgUITest {
    @MockBean
    private MoodRepository moodRepository;
    @Autowired
    private TgUI tgUI;

    @Test
    void whenBuildButtonsThenReturnsKeyboardWithAllMoods() {
        Mood mood1 = Mood.builder().good(true).text("Good").build();
        Mood mood2 = Mood.builder().good(false).text("Bad").build();
        when(moodRepository.findAll()).thenReturn(Arrays.asList(mood1, mood2));

        InlineKeyboardMarkup markup = tgUI.buildButtons();

        verify(moodRepository).findAll();

        assertThat(markup.getKeyboard()).hasSize(2);
        assertThat(markup.getKeyboard()
                .iterator().next()
                .iterator().next()
                .getText()).isEqualTo("Good");
    }
}
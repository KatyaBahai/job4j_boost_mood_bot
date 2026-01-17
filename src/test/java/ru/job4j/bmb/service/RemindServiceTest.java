package ru.job4j.bmb.service;

import org.junit.jupiter.api.Test;
import ru.job4j.bmb.content.SendContent;
import ru.job4j.bmb.repository.MoodLogRepository;
import ru.job4j.bmb.telegramapi.TgUI;
import ru.job4j.bmb.content.Content;
import ru.job4j.bmb.model.Mood;
import ru.job4j.bmb.model.User;
import ru.job4j.bmb.repository.FakeMoodRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RemindServiceTest {
    @Test
    public void whenMoodGood() {
        var result = new ArrayList<Content>();
        var sendContent = new SendContent() {
            @Override
            public void send(Content content) {
                result.add(content);
            }
        };

        var moodRepository = new FakeMoodRepository();
        moodRepository.add(Mood.builder()
                .text("Good")
                .good(true)
                .build());

        var moodLogRepository = mock(MoodLogRepository.class);
        var user = new User();
        user.setChatId(100);
        when(moodLogRepository.findUsersWhoDidNotVoteToday(anyLong(), anyLong()))
                .thenReturn(List.of(user));

        var tgUI = new TgUI(moodRepository);
        new RemindService(sendContent, moodLogRepository, tgUI)
                .remindUsers();
        assertThat(result.iterator().next().getMarkup().getKeyboard()
                .iterator().next().iterator().next().getText()).isEqualTo("Good");
    }
}
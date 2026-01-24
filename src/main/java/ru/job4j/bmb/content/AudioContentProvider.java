package ru.job4j.bmb.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class AudioContentProvider implements ContentProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(AudioContentProvider.class);
    private static final int NUMBER_OF_AUDIOS = 4;

    @Override
    public Content byMood(Long chatId, Long moodId) {
        var content = new Content(chatId);
        int audioNumber = Randomizer.getRandomNumber(NUMBER_OF_AUDIOS);
        File audioFile = new File("./audio/" + audioNumber + ".mp3");

        if (!audioFile.exists() || audioFile.length() == 0) {
            LOGGER.warn("Audio file missing: {}", audioFile.getPath());
            content.setText("Audio temporarily unavailable");
            return content;
        }

        content.setAudio(new InputFile(audioFile, audioNumber + ".mp3"));
        return content;
    }
}

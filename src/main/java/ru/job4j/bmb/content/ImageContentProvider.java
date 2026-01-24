package ru.job4j.bmb.content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.springframework.stereotype.Component;

@Component
public class ImageContentProvider implements ContentProvider {
    private static final int NUMBER_OF_IMAGES = 6;

    @Override
    public Content byMood(Long chatId, Long moodId) {
        var content = new Content(chatId);
        int imageNumber = Randomizer.getRandomNumber(NUMBER_OF_IMAGES);

        File imageFile = new File("./images/" + imageNumber + ".jpg");

        if (!imageFile.exists() || imageFile.length() == 0) {
            throw new IllegalStateException("Image file not found: " + imageFile.getPath());
        }
        content.setPhoto(new InputFile(imageFile, imageNumber + ".jpg"));
        return content;
    }
}

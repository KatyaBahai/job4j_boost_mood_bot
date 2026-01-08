package ru.job4j.bmb.content;

import java.io.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.springframework.stereotype.Component;

@Component
public class ImageContentProvider implements ContentProvider {
    @Override
    public Content byMood(Long chatId, Long moodId) {
        var content = new Content(chatId);
        var imageFile = new File("./images/logo.jpg");
        content.setPhoto(new InputFile(imageFile));
        return content;
    }
}

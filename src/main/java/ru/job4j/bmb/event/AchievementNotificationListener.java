package ru.job4j.bmb.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.job4j.bmb.content.Content;
import ru.job4j.bmb.content.SendContent;

@NoArgsConstructor
@Getter
@Setter
@Component
public class AchievementNotificationListener {
    private SendContent sendContent;

    public AchievementNotificationListener(SendContent sendContent) {
        this.sendContent = sendContent;
    }

    @EventListener
    public void handleAchievementUnlocked(AchievementEvent event) {
        Content content = Content.builder()
                .chatId(event.getChatId())
                .text(event.getAchievementText())
                .build();
        sendContent.send(content);
    }
}

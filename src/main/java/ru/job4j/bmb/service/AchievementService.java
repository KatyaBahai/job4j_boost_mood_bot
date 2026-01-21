package ru.job4j.bmb.service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.bmb.content.Content;
import ru.job4j.bmb.content.SendContent;
import ru.job4j.bmb.event.AchievementEvent;
import ru.job4j.bmb.event.UserEvent;
import ru.job4j.bmb.model.Achievement;
import ru.job4j.bmb.model.Award;
import ru.job4j.bmb.model.MoodLog;
import ru.job4j.bmb.repository.AchievementRepository;
import org.springframework.stereotype.Service;
import ru.job4j.bmb.repository.AwardRepository;
import ru.job4j.bmb.repository.MoodLogRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AchievementService implements ApplicationListener<UserEvent> {
    private final AchievementRepository achievementRepository;
    private final MoodLogRepository moodLogRepository;
    private final AwardRepository awardRepository;
    private final ApplicationEventPublisher eventsPublisher;

    public List<Achievement> findAllByUserId(Long userId) {
        return achievementRepository.findAllByUserId(userId);
    }

    @Transactional
    @Override
    public void onApplicationEvent(UserEvent event) {
        var user = event.getUser();

        List<MoodLog> recentMoodLogs = moodLogRepository
                .findByUserIdOrderByCreationDateDescYearLimit(user.getId());

        int goodStreakDays = 0;
        for (MoodLog moodLog : recentMoodLogs) {
            if (!moodLog.getMood().isGood()) {
                break;
            }
            goodStreakDays++;
        }

        Optional<Award> awardOpt = awardRepository.findByDays(goodStreakDays);
        if (awardOpt.isEmpty()) {
            return;
        }
        Award award = awardOpt.get();
        achievementRepository.save(Achievement.builder()
                .award(award)
                .creationDate(Instant.now().toEpochMilli())
                .user(user)
                .build());
        eventsPublisher.publishEvent(new AchievementEvent(user.getChatId(), award.getTitle()));
    }
}

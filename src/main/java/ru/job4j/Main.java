package ru.job4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.job4j.bmb.model.Award;
import ru.job4j.bmb.model.Mood;
import ru.job4j.bmb.model.MoodContent;
import ru.job4j.bmb.model.User;
import ru.job4j.bmb.repository.*;
import ru.job4j.bmb.telegramapi.TelegramBotService;

import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy
public class Main {
    public static void main(String[] args) throws TelegramApiException {

        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        TelegramBotService bot = context.getBean(TelegramBotService.class);

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);

        System.out.println("✅ Bot registered: " + bot.getBotUsername());
    }

    @Bean
    CommandLineRunner loadDatabase(MoodRepository moodRepository,
                                   MoodContentRepository moodContentRepository,
                                   AwardRepository awardRepository,
                                   UserRepository userRepository,
                                   AchievementRepository achievementRepository,
                                   MoodLogRepository moodLogRepository
    ) {
        return args -> {
            moodLogRepository.deleteAll();
            Thread.sleep(3000);
            achievementRepository.deleteAll();
            Thread.sleep(3000);
            awardRepository.deleteAll();
            Thread.sleep(3000);
            userRepository.deleteAll();
            Thread.sleep(3000);
            moodContentRepository.deleteAll();
            Thread.sleep(3000);
            moodRepository.deleteAll();
            var moods = moodRepository.findAll();
            if (!moods.isEmpty()) {
                return;
            }
            var data = new ArrayList<MoodContent>();
            data.add(MoodContent.builder()
                    .mood(Mood.builder()
                            .good(true)
                            .text("Счастливейший на свете \uD83D\uDE0E")
                            .build())
                    .text("Невероятно! Вы сияете от счастья! Поделитесь своей радостью с близкими :)")
                    .build());
            data.add(MoodContent.builder()
                    .mood(Mood.builder()
                            .good(true)
                            .text("В состоянии комфорта и гармонии ☺\uFE0F")
                            .build())
                    .text("Потрясающе! В таком состоянии можно горы свернуть.")
                    .build());
            data.add(MoodContent.builder()
                    .mood(Mood.builder()
                            .good(false)
                            .text("Тревожное состояние \uD83D\uDE1F")
                            .build())
                    .text("""
                            Всё пройдет, и это пройдет.
                            Попробуйте расслабиться, помедитировать и найти источник вашего беспокойства.
                            """)
                    .build());
            data.add(MoodContent.builder()
                    .mood(Mood.builder()
                            .good(false)
                            .text("Всё плохо \uD83D\uDE20")
                            .build())
                    .text("""
                            Сочувствую вам! У всех бывает плохое настроение.
                            Поговорите с близким человеком и попросите о помощи.
                            """)
                    .build());
            data.add(MoodContent.builder()
                    .mood(Mood.builder()
                            .good(true)
                            .text("Совершенно непонятно \uD83D\uDE0E")
                            .build())
                    .text("Ой, а вы завтракали сегодня?")
                    .build());

            moodRepository.saveAll(data.stream().map(MoodContent::getMood).toList());
            moodContentRepository.saveAll(data);

            var awards = new ArrayList<Award>();
            awards.add(Award.builder()
                    .days(1)
                    .title("Ваша награда за 1 день хорошего настроения: Смайлик дня")
                    .description("""
                            За 1 день хорошего настроения.
                            Награда: Веселый смайлик или стикер,
                            отправленный пользователю в качестве поощрения.
                            """)
                    .build());
            awards.add(Award.builder()
                    .days(5)
                    .title("Ваша награда за 5 дней хорошего настроения: Персонализированные рекомендации")
                    .description("""
                            После 5 дней хорошего настроения.
                            Награда: Подборка контента или активности
                            на основе интересов пользователя.
                            """)
                    .build());
            awards.add(Award.builder()
                    .days(30)
                    .title("Ваша награда за 30 дней хорошего настроения: статус 'Настроение месяца'")
                    .description("""
                            За поддержание хорошего или отличного настроения
                            в течение целого месяца. Награда: Специальный значок,
                            признание в сообществе или дополнительные привилегии.
                            """)
                    .build());
            awards.add(Award.builder()
                    .days(20)
                    .title("Ваша награда за 20 дней хорошего настроения: титул 'Лучезарный'")
                    .description("""
                            За 20 дней хорошего или отличного настроения.
                            Награда: Специальный титул, отображаемый
                            рядом с именем пользователя.
                            """)
                    .build());
            awards.add(Award.builder()
                    .days(50)
                    .title("Ваша награда за 50 дней хорошего настроения: Персональное поздравление")
                    .description("""
                            За значимые достижения (например, 50 дней хорошего настроения).
                            Награда: Персонализированное сообщение от
                            команды приложения или вдохновляющая цитата.
                            """)
                    .build());
            awardRepository.saveAll(awards);
            List<Award> awardList = (List<Award>) awardRepository.findAll();
            awardList.forEach(System.out::println);
        };
    }
}
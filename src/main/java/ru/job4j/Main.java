package ru.job4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.job4j.bmb.model.Award;
import ru.job4j.bmb.model.Mood;
import ru.job4j.bmb.model.MoodContent;
import ru.job4j.bmb.repository.AwardRepository;
import ru.job4j.bmb.repository.MoodContentRepository;
import ru.job4j.bmb.repository.MoodRepository;

import java.util.ArrayList;

@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner loadDatabase(MoodRepository moodRepository,
                                   MoodContentRepository moodContentRepository,
                                   AwardRepository awardRepository) {
        return args -> {
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
                    .text("Невероятно! Вы сияете от счастья, продолжайте радоваться жизни.")
                    .build());
            data.add(MoodContent.builder()
                    .mood(Mood.builder()
                            .good(true)
                            .text("В состоянии комфорта ☺\uFE0F")
                            .build())
                    .text("Потрясающе! Вы в состоянии внутреннего мира и гармонии.")
                    .build());
            data.add(MoodContent.builder()
                    .mood(Mood.builder()
                            .good(false)
                            .text("Тревожное настроение \uD83D\uDE1F")
                            .build())
                    .text("""
                            Не волнуйтесь, всё пройдет.
                            Попробуйте расслабиться и найти источник вашего беспокойства.
                            """)
                    .build());
            data.add(MoodContent.builder()
                    .mood(Mood.builder()
                            .good(false)
                            .text("Раздраженное настроение \uD83D\uDE20")
                            .build())
                    .text("""
                    Попробуйте успокоиться 
                    и найти причину раздражения, чтобы исправить ситуацию.
                    """)
                    .build());
            data.add(MoodContent.builder()
                    .mood(Mood.builder()
                            .good(true)
                            .text("Счастливейший на свете \uD83D\uDE0E")
                            .build())
                    .text("Невероятно! Вы сияете от счастья, продолжайте радоваться жизни.")
                    .build());

            moodRepository.saveAll(data.stream().map(MoodContent::getMood).toList());
            moodContentRepository.saveAll(data);

            var awards = new ArrayList<Award>();
            awards.add(Award.builder()
                    .days(1)
                    .title("Смайлик дня")
                    .description("""
                            За 1 день хорошего настроения.
                            Награда: Веселый смайлик или стикер,
                            отправленный пользователю в качестве поощрения.
                            """)
                    .build());
            awards.add(Award.builder()
                    .days(5)
                    .title("Персонализированные рекомендации")
                    .description("""
                            После 5 дней хорошего настроения.
                            Награда: Подборка контента или активности
                            на основе интересов пользователя.
                            """)
                    .build());
            awards.add(Award.builder()
                    .days(30)
                    .title("Награда 'Настроение месяца'")
                    .description("""
                            За поддержание хорошего или отличного настроения
                            в течение целого месяца. Награда: Специальный значок,
                            признание в сообществе или дополнительные привилегии.
                            """)
                    .build());
            awards.add(Award.builder()
                    .days(20)
                    .title("Титул 'Лучезарный'")
                    .description("""
                            За 20 дней хорошего или отличного настроения.
                            Награда: Специальный титул, отображаемый
                            рядом с именем пользователя.
                            """)
                    .build());
            awards.add(Award.builder()
                    .days(50)
                    .title("Персональное поздравление")
                    .description("""
                            За значимые достижения (например, 50 дней хорошего настроения).
                            Награда: Персонализированное сообщение от 
                            команды приложения или вдохновляющая цитата.
                            """)
                    .build());
            awardRepository.saveAll(awards);
        };
    }
}
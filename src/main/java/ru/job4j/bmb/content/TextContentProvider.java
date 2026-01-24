package ru.job4j.bmb.content;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TextContentProvider implements ContentProvider {
    private static final int NUMBER_OF_TEXTS = 7;
    List<String> textList = new ArrayList<>();

    {
        textList.add("Съешь шоколадку и объяви себя императором. На 10 минут. ");
        textList.add("""
                Сделай 5 пушей в пустой репозиторий с коммитами
                 refactor air. GitHub подумает, что ты гений минимализма. 
                 Бонус: +5 к вкладу в open source (ну, почти).""");
        textList.add("Dockerize свой кофе: FROM python:3.12 RUN pip install caffeine && CMD ['brew', 'mood_boost']. docker run --rm — и контейнер сам себя подзарядит!");
        textList.add("Расскажи анекдот своему холодильнику. Он не посмеётся, но и яйцами не закидает. А ведь они у него скорее всего есть!");
        textList.add("Выпей чашку чая с лимоном и вообрази, что это эликсир от NullPointerException. ");
        textList.add("Съешь йогурт вилкой. Переключи мозг в режим \"хаос\". ");
        textList.add("Выпей чай и напиши def mood_fix(): print(\"\uD83D\uDE0E\") — запусти в Jupyter. Если не подняло — добавь import random; jokes = [...] и вечный цикл смеха!");
    }

    @Override
    public Content byMood(Long chatId, Long moodId) {
        var content = new Content(chatId);
        int textNumber = Randomizer.getRandomNumber(NUMBER_OF_TEXTS);
        content.setText(textList.get(textNumber));
        return content;
    }
}

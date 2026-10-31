package ru.yandex.practicum.functions;

import ru.yandex.practicum.enums.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;

public class BadQualityCountFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long count = sessions.stream()
                .filter(s -> s.quality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult("Количество сессий с плохим качеством сна", count);
    }
}

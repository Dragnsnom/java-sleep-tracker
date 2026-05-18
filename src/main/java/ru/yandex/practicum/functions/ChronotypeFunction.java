package ru.yandex.practicum.functions;

import ru.yandex.practicum.enums.Chronotype;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChronotypeFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Chronotype", Chronotype.PIGEON.getDisplayName());
        }

        Map<Chronotype, Long> counts = sessions.stream()
                .filter(session -> !isShortDaySleep(session))
                .map(this::classifyNight)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        if (counts.isEmpty()) {
            return new SleepAnalysisResult("Chronotype", Chronotype.PIGEON.getDisplayName());
        }

        Chronotype result = counts.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(Chronotype.PIGEON);

        long maxCount = counts.get(result);
        if (counts.values().stream().filter(c -> c == maxCount).count() > 1) {
            result = Chronotype.PIGEON;
        }

        return new SleepAnalysisResult("Хронотип", result.getDisplayName());
    }

    private boolean isShortDaySleep(SleepingSession session) {
        long durationMinutes = session.getDurationMinutes();
        LocalTime start = session.start().toLocalTime();

        return durationMinutes < 240 &&
                start.isAfter(LocalTime.of(8, 0)) &&
                start.isBefore(LocalTime.of(20, 0));
    }

    private Chronotype classifyNight(SleepingSession session) {
        int bedHour = session.start().getHour();
        int bedMinute = session.start().getMinute();
        int wakeHour = session.end().getHour();
        int wakeMinute = session.end().getMinute();

        double bedTime = bedHour + bedMinute / 60.0;
        double wakeTime = wakeHour + wakeMinute / 60.0;

        // Если заснули после полуночи, но до 6 утра, добавляем 24 часа
        if (bedTime < 6.0) {
            bedTime += 24.0;
        }

        // Сова: bedTime > 23.0 AND wakeTime > 9.0
        if (bedTime > 23.0 && wakeTime > 9.0) {
            return Chronotype.OWL;
        }

        // Жаворонок: bedTime < 22.0 AND wakeTime < 7.0
        if (bedTime < 22.0 && wakeTime < 7.0) {
            return Chronotype.LARK;
        }

        return Chronotype.PIGEON;
    }
}
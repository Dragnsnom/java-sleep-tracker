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
    private final double AFTER_MIDNIGHT_THRESHOLD = 6.0;
    private final double HOURS_IN_DAY = 24.0;
    private final double OWL_BED_THRESHOLD = 23.0;
    private final double OWL_WAKE_THRESHOLD = 9.0;
    private final double LARK_BED_THRESHOLD = 22.0;
    private final double LARK_WAKE_THRESHOLD = 7.0;

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
        long typesWithMaxCount = counts.values().stream()
                .filter(count -> count == maxCount)
                .count();

        if (typesWithMaxCount > 1) {
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

        if (bedTime < AFTER_MIDNIGHT_THRESHOLD) {
            bedTime += HOURS_IN_DAY;
        }

        // Проверка на сову
        boolean isOwl = bedTime > OWL_BED_THRESHOLD && wakeTime > OWL_WAKE_THRESHOLD;

        // Проверка на жаворонка
        boolean isLark = bedTime < LARK_BED_THRESHOLD && wakeTime < LARK_WAKE_THRESHOLD;

        if (isOwl) {
            return Chronotype.OWL;
        }
        if (isLark) {
            return Chronotype.LARK;
        }
        return Chronotype.PIGEON;
    }

}
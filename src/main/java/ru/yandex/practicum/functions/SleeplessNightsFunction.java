package ru.yandex.practicum.functions;


import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class SleeplessNightsFunction implements SleepAnalysisFunction {

    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Общее количество сессий сна", 0L);
        }

        LocalDateTime firstStart = sessions.getFirst().start();
        LocalDateTime lastEnd = sessions.getLast().end();

        // Получаем первую ночь для проверки
        LocalDate firstNight = getFirstNight(firstStart);
        // Получаем последнюю ночь для проверки
        LocalDate lastNight = getLastNight(lastEnd);

        long sleeplessNights = 0;
        LocalDate currentNight = firstNight;

        while (!currentNight.isAfter(lastNight)) {
            if (isSleeplessNight(currentNight, sessions)) {
                sleeplessNights++;
            }
            currentNight = currentNight.plusDays(1);
        }

        return new SleepAnalysisResult("Общее количество сессий сна", sleeplessNights);
    }

    private LocalDate getFirstNight(LocalDateTime firstStart) {
        LocalTime time = firstStart.toLocalTime();
        LocalDate date = firstStart.toLocalDate();

        // Если первая сессия началась после 6 утра, то первая ночь - это предыдущая
        if (time.isAfter(NIGHT_END)) {
            return date;
        }
        // Если началась до 6 утра, то это продолжение предыдущей ночи
        return date.minusDays(1);
    }

    private LocalDate getLastNight(LocalDateTime lastEnd) {
        LocalTime time = lastEnd.toLocalTime();
        LocalDate date = lastEnd.toLocalDate();

        // Если последняя сессия закончилась до 6 утра, то последняя ночь - это предыдущая
        if (time.isBefore(NIGHT_START) || time.isBefore(NIGHT_END)) {
            return date.minusDays(1);
        }
        // Если закончилась после 6 утра, то эта ночь уже не считается
        return date.minusDays(1);
    }

    private boolean isSleeplessNight(LocalDate night, List<SleepingSession> sessions) {
        LocalDateTime nightStart = LocalDateTime.of(night, NIGHT_START);
        LocalDateTime nightEnd = LocalDateTime.of(night, NIGHT_END);

        for (SleepingSession session : sessions) {
            LocalDateTime sessionStart = session.start();
            LocalDateTime sessionEnd = session.end();


            if (sessionStart.isBefore(nightEnd) && sessionEnd.isAfter(nightStart)) {
                return false;
            }
        }

        return true;
    }
}
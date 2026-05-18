package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.enums.SleepQuality;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record SleepingSession(LocalDateTime start, LocalDateTime end, SleepQuality quality) {

    public long getDurationMinutes() {
        return ChronoUnit.MINUTES.between(start, end);
    }

    @Override
    public String toString() {
        return String.format("%s - %s [%s]", start, end, quality);
    }
}
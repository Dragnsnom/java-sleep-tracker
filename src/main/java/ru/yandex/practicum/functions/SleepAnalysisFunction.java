package ru.yandex.practicum.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;

@FunctionalInterface
public interface SleepAnalysisFunction {
    SleepAnalysisResult apply(List<SleepingSession> sessions);
}

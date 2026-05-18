package ru.yandex.practicum.sleeptracker;


public record SleepAnalysisResult(String description, Object value) {

    @Override
    public String toString() {
        return description + ": " + value;
    }
}

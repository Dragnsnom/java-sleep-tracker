package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.enums.SleepQuality;
import ru.yandex.practicum.functions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SleepTrackerApp {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private final List<SleepAnalysisFunction> functions = new ArrayList<>();

    public SleepTrackerApp() {
        // Добавляем все функции анализа
        addFunction(sessions -> new SleepAnalysisResult("Total sleeping sessions", sessions.size()));
        addFunction(new MinDurationFunction());
        addFunction(new MaxDurationFunction());
        addFunction(new AvgDurationFunction());
        addFunction(new BadQualityCountFunction());
        addFunction(new SleeplessNightsFunction());
        addFunction(new ChronotypeFunction());
    }

    public void addFunction(SleepAnalysisFunction function) {
        functions.add(function);
    }

    public List<SleepingSession> loadSessions(String filePath) throws IOException {
        return Files.lines(Path.of(filePath))
                .filter(line -> !line.isBlank())
                .map(this::parseLine)
                .collect(Collectors.toList());
    }

    private SleepingSession parseLine(String line) {
        String[] parts = line.split(";");
        LocalDateTime start = LocalDateTime.parse(parts[0], FORMATTER);
        LocalDateTime end = LocalDateTime.parse(parts[1], FORMATTER);
        SleepQuality quality = SleepQuality.valueOf(parts[2]);
        return new SleepingSession(start, end, quality);
    }

    public void runAnalysis(String filePath) throws IOException {
        List<SleepingSession> sessions = loadSessions(filePath);

        System.out.println("Результат:\n");

        functions.stream()
                .map(fn -> fn.apply(sessions))
                .forEach(System.out::println);
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java SleepTrackerApp <logfile>");
            System.err.println("Example: java SleepTrackerApp sleep_log.txt");
            return;
        }

        SleepTrackerApp app = new SleepTrackerApp();
        app.runAnalysis(args[0]);
    }
}
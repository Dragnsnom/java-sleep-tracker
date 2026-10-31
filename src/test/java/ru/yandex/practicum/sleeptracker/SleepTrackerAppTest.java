package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.enums.SleepQuality;
import ru.yandex.practicum.functions.AvgDurationFunction;
import ru.yandex.practicum.functions.BadQualityCountFunction;
import ru.yandex.practicum.functions.ChronotypeFunction;
import ru.yandex.practicum.functions.MaxDurationFunction;
import ru.yandex.practicum.functions.MinDurationFunction;
import ru.yandex.practicum.functions.SleeplessNightsFunction;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleepTrackerAppTest {
    // ==================== ТЕСТЫ ДЛЯ MIN DURATION ====================

    @Test
    void testMinDurationWithMultipleSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD
                ), // 600 минут
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 1, 0),
                        SleepQuality.NORMAL
                ), // 120 минут
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 0, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 30),
                        SleepQuality.GOOD
                ) // 450 минут
        );

        MinDurationFunction function = new MinDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(120L, result.value());
    }

    @Test
    void testMinDurationWithSingleSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ) // 480 минут
        );

        MinDurationFunction function = new MinDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(480L, result.value());
    }

    @Test
    void testMinDurationWithEmptyList() {
        List<SleepingSession> sessions = List.of();

        MinDurationFunction function = new MinDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(0L, result.value());
    }

    // ==================== ТЕСТЫ ДЛЯ MAX DURATION ====================

    @Test
    void testMaxDurationWithMultipleSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD
                ), // 600 минут
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 10, 0),
                        SleepQuality.NORMAL
                ), // 660 минут
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 0, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        SleepQuality.GOOD
                ) // 360 минут
        );

        MaxDurationFunction function = new MaxDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(660L, result.value());
    }

    @Test
    void testMaxDurationWithSingleSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD
                ) // 630 минут
        );

        MaxDurationFunction function = new MaxDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(630L,result.value());
    }

    // ==================== ТЕСТЫ ДЛЯ AVG DURATION ====================

    @Test
    void testAvgDurationWithMultipleSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD
                ), // 600 минут
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL
                ), // 480 минут
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 0, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        SleepQuality.GOOD
                ) // 360 минут
        );

        AvgDurationFunction function = new AvgDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);

        // (600 + 480 + 360) / 3 = 480
        assertEquals(480.0, (Double) result.value());
    }

    @Test
    void testAvgDurationWithSingleSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 30),
                        SleepQuality.GOOD
                ) // 510 минут
        );

        AvgDurationFunction function = new AvgDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(510.0, (Double) result.value());
    }

    @Test
    void testAvgDurationWithEmptyList() {
        List<SleepingSession> sessions = List.of();

        AvgDurationFunction function = new AvgDurationFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(0.0, (Double) result.value());
    }

    // ==================== ТЕСТЫ ДЛЯ BAD QUALITY COUNT ====================

    @Test
    void testBadQualityCountWithMultipleBadSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 0, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 4, 1, 0),
                        LocalDateTime.of(2025, 10, 4, 9, 0),
                        SleepQuality.NORMAL
                )
        );

        BadQualityCountFunction function = new BadQualityCountFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(2L, result.value());
    }

    @Test
    void testBadQualityCountWithNoBadSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL
                )
        );

        BadQualityCountFunction function = new BadQualityCountFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(0L, result.value());
    }

    @Test
    void testBadQualityCountWithAllBadSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.BAD
                )
        );

        BadQualityCountFunction function = new BadQualityCountFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(2L, result.value());
    }

    // ==================== ТЕСТЫ ДЛЯ SLEEPLESS NIGHTS ====================


    @Test
    void testSleeplessNightsWithOneSleeplessNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD
                ),
                // Ночь с 2 на 3 октября без сна
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        LocalDateTime.of(2025, 10, 3, 11, 0),
                        SleepQuality.NORMAL
                )
        );

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(1L, result.value());
    }

    @Test
    void testSleeplessNightsWithMultipleSleeplessNights() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD
                ),
                // Ночь с 2 на 3 октября без сна
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 14, 0),
                        LocalDateTime.of(2025, 10, 3, 15, 0),
                        SleepQuality.NORMAL
                ),
                // Ночь с 3 на 4 октября без сна
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 4, 10, 0),
                        LocalDateTime.of(2025, 10, 4, 12, 0),
                        SleepQuality.NORMAL
                )
        );

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(2L, result.value());
    }

    @Test
    void testSleeplessNightsWithSleepCrossingMidnight() {
        // Сон пересекает интервал 0:00-6:00, поэтому ночь не бессонная
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 3, 0),
                        SleepQuality.GOOD
                )
        );

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(1L, result.value());
    }

    // ==================== ТЕСТЫ ДЛЯ CHRONOTYPE ====================

    @Test
    void testChronotypeOwl() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 0, 30),
                        LocalDateTime.of(2025, 10, 1, 10, 0),
                        SleepQuality.GOOD
                )
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Owl (Ночная сова)", result.value());
    }

    @Test
    void testChronotypeLark() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 30),
                        SleepQuality.GOOD
                )
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Lark (Жаворонок)", result.value());
    }

    @Test
    void testChronotypePigeon() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 30),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD
                )
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Pigeon (Голубь)",result.value());
    }

    @Test
    void testChronotypeWithMultipleNightsOwlDominant() {
        List<SleepingSession> sessions = List.of(
                // Ночь 1: Сова
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 0, 30),
                        LocalDateTime.of(2025, 10, 1, 10, 0),
                        SleepQuality.GOOD
                ),
                // Ночь 2: Голубь
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 30),
                        LocalDateTime.of(2025, 10, 3, 8, 0),
                        SleepQuality.NORMAL
                ),
                // Ночь 3: Сова
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 4, 1, 0),
                        LocalDateTime.of(2025, 10, 4, 9, 30),
                        SleepQuality.GOOD
                )
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Owl (Ночная сова)", result.value());
    }

    @Test
    void testChronotypeIgnoresShortDaySleep() {
        List<SleepingSession> sessions = List.of(
                // Дневной сон (менее 4 часов)
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 14, 0),
                        LocalDateTime.of(2025, 10, 1, 15, 0),
                        SleepQuality.NORMAL
                ),
                // Ночной сон - сова
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 0, 30),
                        LocalDateTime.of(2025, 10, 2, 10, 0),
                        SleepQuality.GOOD
                )
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Owl (Ночная сова)",result.value());
    }

    @Test
    void testChronotypeTieGoesToPigeon() {
        List<SleepingSession> sessions = List.of(
                // Сова
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 0, 30),
                        LocalDateTime.of(2025, 10, 1, 10, 0),
                        SleepQuality.GOOD
                ),
                // Жаворонок
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 21, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 30),
                        SleepQuality.GOOD
                ),
                // Голубь (для ничьей 1:1)
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 4, 22, 30),
                        LocalDateTime.of(2025, 10, 5, 8, 0),
                        SleepQuality.NORMAL
                )
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Pigeon (Голубь)", result.value());
    }

    // ==================== ТЕСТЫ ДЛЯ TOTAL SESSIONS ====================

    @Test
    void testTotalSessionsCount() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 14, 0),
                        LocalDateTime.of(2025, 10, 3, 15, 0),
                        SleepQuality.NORMAL
                )
        );

        java.util.function.Function<List<SleepingSession>, SleepAnalysisResult> totalSessions =
                s -> new SleepAnalysisResult("Общее количество сессий сна", s.size());

        SleepAnalysisResult result = totalSessions.apply(sessions);

        assertEquals(3, result.value());
    }
}
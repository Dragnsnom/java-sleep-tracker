package ru.yandex.practicum.enums;

public enum Chronotype {
    OWL("Owl (Ночная сова)"),
    LARK("Lark (Жаворонок)"),
    PIGEON("Pigeon (Голубь)");

    private final String displayName;

    Chronotype(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

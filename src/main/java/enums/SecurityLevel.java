package main.java.enums;

public enum SecurityLevel {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    VERY_HIGH(4),
    MAXIMUM(5);

    private final int level;

    SecurityLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean isHigherThan(SecurityLevel other) {
        return this.compareTo(other) > 0;
    }

    public boolean isLowerThan(SecurityLevel other) {
        return this.compareTo(other) < 0;
    }
}

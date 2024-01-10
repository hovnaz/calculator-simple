package tech.it.mentor;

public enum Priority {
    LOW(1),
    HIGH(2);

    private final int value;

    Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

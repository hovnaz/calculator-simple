package tech.it.mentor;

public abstract class CalculatorOperator implements ArithmeticOperation {

    private final String name;
    private final Priority priority;
    private final String operator;

    public CalculatorOperator(String name, String operator, Priority priority) {
        this.name = name;
        this.operator = operator;
        this.priority = priority;
    }

    public String getOperator() {
        return operator;
    }

    public String getName() {
        return name;
    }

    public Priority getPriority() {
        return priority;
    }
}

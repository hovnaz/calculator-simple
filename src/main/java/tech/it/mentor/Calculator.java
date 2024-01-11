package tech.it.mentor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private final Map<String, CalculatorOperator> operators = new HashMap<>();

    public Calculator() {
        initializeOperators();
    }

    public BigDecimal calc(String input) {
        input = input.replaceAll("\\s", "");
        validateExpression(input);
        BigDecimal result = performCalculation(input);
        return roundToIntegerOrZero(result);
    }

    private void validateExpression(String expression) {
        extractNumbers(expression);
        if (!expression.matches(".*[0-9]+[+\\-*/]+[0-9]+.*") ||
                expression.matches(".*[+\\-*/]{2,}.*") ||
                expression.matches(".*[+\\-*/]$")) {
            throw new InvalidExpressionException("Invalid expression: " + expression);
        }
    }

    public static void extractNumbers(String inputString) {

        Pattern pattern = Pattern.compile("\\d+\\.\\d+|\\d+");
        Matcher matcher = pattern.matcher(inputString);

        while (matcher.find()) {
            String number = matcher.group();
            String numberInt = number.contains(".") ? number.substring(0, number.indexOf('.')) : number;
            if (numberInt.length() > 1 && numberInt.charAt(0) == '0') {
                throw new InvalidExpressionException("Invalid number: " + number);
            }
        }
    }

    private int findFirstPriorityOperatorIndex(String input) {
        int lowestPriority = 0;
        int operatorIndex = -1;

        for (CalculatorOperator operator : operators.values()) {
            int index = input.indexOf(operator.getOperator());

            if (index != -1 && (operator.getPriority().getValue() >= lowestPriority && (index < operatorIndex || operatorIndex == -1) || operator.getPriority().getValue() > lowestPriority)) {
                lowestPriority = operator.getPriority().getValue();
                operatorIndex = index;
            }
        }

        return operatorIndex;
    }

    private String performOperationAtIndex(String expression, int operatorIndex) {
        String[] leftOperands = expression.substring(0, operatorIndex).split("[\\+\\-\\*\\/]");
        String[] rightOperands = expression.substring(operatorIndex + 1).split("[\\+\\-\\*\\/]");

        BigDecimal leftOperand = new BigDecimal(leftOperands[leftOperands.length - 1]);
        BigDecimal rightOperand = new BigDecimal(rightOperands[0]);
        BigDecimal result = operators.get(String.valueOf(expression.charAt(operatorIndex))).performOperation(leftOperand, rightOperand);

        StringBuilder builder = new StringBuilder(expression);
        builder.replace(operatorIndex - leftOperand.toString().length(), operatorIndex + rightOperand.toString().length() + 1, result.toString());
        return builder.toString();
    }

    private BigDecimal performCalculation(String expression) {

        while (true) {
            int priorityOperatorIndex = findFirstPriorityOperatorIndex(expression);
            if (priorityOperatorIndex == -1) {
                return new BigDecimal(expression);
            }
            expression = performOperationAtIndex(expression, priorityOperatorIndex);
        }
    }

    private void addOperator(String name, String symbol, Priority priority, BiFunction<BigDecimal, BigDecimal, BigDecimal> operation) {
        operators.put(symbol, new CalculatorOperator(name, symbol, priority) {
            @Override
            public BigDecimal performOperation(BigDecimal leftOperand, BigDecimal rightOperand) {
                return operation.apply(leftOperand, rightOperand);
            }
        });
    }

    private void initializeOperators() {
        addOperator("plus", "+", Priority.LOW, BigDecimal::add);
        addOperator("minus", "-", Priority.LOW, BigDecimal::subtract);
        addOperator("multiply", "*", Priority.HIGH, BigDecimal::multiply);
        addOperator("divide", "/", Priority.HIGH, (left, right) -> {
            if (right.equals(BigDecimal.ZERO)) {
                throw new InvalidExpressionException("Cannot divide by zero");
            }
            return left.divide(right, 2, RoundingMode.HALF_UP);
        });
    }

    private static BigDecimal roundToIntegerOrZero(BigDecimal originalResult) {

        return originalResult.signum() == 0 ? BigDecimal.ZERO : originalResult.stripTrailingZeros();
    }
}

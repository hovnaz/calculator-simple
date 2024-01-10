package tech.it.mentor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Calculator {

    private static final Map<String, CalculatorOperator> operators = new HashMap<>();


    static {
        setOperator(new CalculatorOperator("plus", "+", Priority.LOW) {
            @Override
            public BigDecimal action(BigDecimal leftOperand, BigDecimal rightOperand) {
                return leftOperand.add(rightOperand);
            }
        });

        setOperator(new CalculatorOperator("minus", "-", Priority.LOW) {
            @Override
            public BigDecimal action(BigDecimal leftOperand, BigDecimal rightOperand) {
                return leftOperand.subtract(rightOperand);
            }
        });
        setOperator(new CalculatorOperator("multiply", "*", Priority.HIGH) {
            @Override
            public BigDecimal action(BigDecimal leftOperand, BigDecimal rightOperand) {
                return leftOperand.multiply(rightOperand);
            }
        });
        setOperator(new CalculatorOperator("divide", "/", Priority.HIGH) {
            @Override
            public BigDecimal action(BigDecimal leftOperand, BigDecimal rightOperand) {
                if (rightOperand.equals(BigDecimal.ZERO)) {
                    throw new InvalidExpressionException("Cannot divide by zero");
                }
                return leftOperand.divide(rightOperand, 2, RoundingMode.HALF_UP);
            }
        });
    }

    private static void setOperator(CalculatorOperator operator) {
        operators.put(operator.getOperator(), operator);
    }

    public BigDecimal calc(String input) {
        input = input.replaceAll("\\s", "");
        validateExpression(input);
        return performCalculation(input);
    }

    private void validateExpression(String expression) {

        if (!expression.matches(".*[0-9]+[+\\-*/]+[0-9]+.*") ||
                expression.matches(".*[+\\-*/]{2,}.*") ||
                expression.matches(".*[+\\-*/]$")) {
            throw new InvalidExpressionException("Invalid expression: " + expression);
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
        BigDecimal result = operators.get(String.valueOf(expression.charAt(operatorIndex))).action(leftOperand, rightOperand);

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
}

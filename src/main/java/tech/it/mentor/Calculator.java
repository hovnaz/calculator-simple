package tech.it.mentor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    public int calculate(String input) {
        List<Integer> operands = extractNumbers(input);
        Integer firstOperand = operands.get(0);
        int secondOperand = operands.get(1);
        char operator = input.charAt(firstOperand.toString().length());
        validateExpression(input);
        validateOperands(firstOperand, secondOperand);

        return performCalculation(firstOperand, operator, secondOperand);
    }

    private int performCalculation(int firstOperand, char operator, int secondOperand) {
        return switch (operator) {
            case '+' -> firstOperand + secondOperand;
            case '-' -> firstOperand - secondOperand;
            case '*' -> firstOperand * secondOperand;
            case '/' -> performDivision(firstOperand, secondOperand);
            default -> throw new InvalidExpressionException("Invalid operator");
        };
    }

    private int performDivision(int firstOperand, int secondOperand) {
        if (secondOperand == 0) {
            throw new InvalidExpressionException("Division by zero");
        }
        return firstOperand / secondOperand;
    }

    private List<Integer> extractNumbers(String inputString) {
        List<Integer> numbers = new ArrayList<>(2);

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(inputString);

        while (matcher.find()) {
            String number = matcher.group();
            try {
                numbers.add(Integer.parseInt(number));
            } catch (NumberFormatException e) {
                throw new InvalidExpressionException("Invalid number format: " + number);
            }
        }

        if (numbers.size() != 2) {
            throw new InvalidExpressionException("Exactly 2 numbers should be present in the input: " + inputString);
        }

        return numbers;
    }

    private void validateExpression(String expression) {
        validateNumbersInExpression(expression);
        if (!expression.matches(".*[0-9]+[+\\-*/]+[0-9]+.*") ||
                expression.matches(".*[+\\-*/]{2,}.*") ||
                expression.matches(".*[+\\-*/]$")) {
            throw new InvalidExpressionException("Invalid expression: " + expression);
        }
    }

    private void validateNumbersInExpression(String inputString) {

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

    private void validateOperands(int firstOperand, int secondOperand) {
        if (isValidOperand(firstOperand) || isValidOperand(secondOperand)) {
            throw new InvalidExpressionException("Invalid operands. Numbers should be between 1 and 10 (inclusive).");
        }
    }

    private boolean isValidOperand(int operand) {
        return operand < 1 || operand > 10;
    }
}

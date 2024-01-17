package tech.it.mentor;

import java.util.Scanner;

public class Main {

    private static final Calculator calculator = new Calculator();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter a mathematical expression (e.g., 4+1): ");
        String expression = scanner.nextLine();
        System.out.println("Result: " + calculator.calculate(expression));
    }
}

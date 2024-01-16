package tech.it.mentor;

public class Main {

    private static final Calculator calculator = new Calculator();

    public static void main(String[] args) {
        System.out.println(calculator.calculate("4+1"));
        System.out.println(calculator.calculate("2*6"));
    }
}

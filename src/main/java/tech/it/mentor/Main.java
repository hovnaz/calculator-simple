package tech.it.mentor;

public class Main {

    private static final Calculator calculator = new Calculator();

    public static void main(String[] args) {
        System.out.println(calculator.calc("1110+2*345+10+10-10/100/10"));
//        System.out.println(calculator.calc("11+10/2*345-1000"));
    }
}

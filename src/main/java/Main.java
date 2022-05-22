import java.util.Scanner;

public class Main {
    private static boolean isDataIncorrect(String var0, String maxVar, Convertible convertible) {
        if (convertible != null && convertible.convertToArabicNumber(var0) <= convertible.convertToArabicNumber(maxVar)) {
            return false;
        } else {
            System.err.println("Максимально допустимое положительное число - " + maxVar);
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println("ПРОГРАММА СЛОЖЕНИЯ ДВУХ РИМСКИХ ЧИСЕЛ");

        Convertible convertible = new RomanNumeralConverter();
        Calculator calculator = new Calculator(convertible);

        String var0, var1, maxVar = "C";

        try (Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.println("Ниже введите первое число (максимально допустимое положительное число - " + maxVar + ")");
                var0 = scanner.nextLine();
            }
            while (isDataIncorrect(var0, maxVar, convertible));

            do {
                System.out.println("Ниже введите второе число (максимально допустимое положительное число - " + maxVar + ")");
                var1 = scanner.nextLine();
            }
            while (isDataIncorrect(var1, maxVar, convertible));

            System.out.printf("Результат выполнения арифметической операции: %s + %s = %s", var0, var1, calculator.sum(var0, var1));
        }
    }
}

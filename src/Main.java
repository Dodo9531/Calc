import Exceptions.ExpressionFormatException;
import Exceptions.NumberValuesException;

import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void String_calc(String input) // Метод для расчётов
    {
        boolean first_num_is_rome = false;
        boolean second_num_is_rome = false; // Показатели того были ли введены римские цифры или арабские
        String Rome_number;

        int first_num;
        int second_num;

        try {
            first_num = Integer.parseInt(input.substring(0, input.indexOf(' ')));
        } catch (NumberFormatException e) // Если вылезло исключение формата чисел значит введена строка которая преобразуется в римское число
        {
            Rome_number = e.getMessage().substring(e.getMessage().indexOf('"') + 1, e.getMessage().lastIndexOf('"'));
            first_num = RomeToArabic(Rome_number);
            first_num_is_rome = true;
        }

        if (first_num > 10) // Проверка не выходит ли число за заданные рамки
        {
            throw new NumberValuesException("First number is greater than 10");
        } else if (first_num < 1) {
            throw new NumberValuesException("First number is less than 1");
        } else {

            try // Повторяются все теже действия но для второго числа
            {
                second_num = Integer.parseInt(input.substring(input.lastIndexOf(' ') + 1));
            } catch (NumberFormatException e) {
                Rome_number = e.getMessage().substring(e.getMessage().indexOf('"') + 1, e.getMessage().lastIndexOf('"'));
                if (Rome_number.isEmpty() || Rome_number.equals("+") || Rome_number.equals("/") || Rome_number.equals("-") || Rome_number.equals("*"))
                    throw new ExpressionFormatException("Expression isn't correct");
                second_num = RomeToArabic(Rome_number);
                second_num_is_rome = true;

            }

            if (first_num_is_rome != second_num_is_rome) {
                throw new NumberValuesException("One of given numbers is rome and other is arabic");
            } else if (second_num > 10) {
                throw new NumberValuesException("Second number is greater than 10");
            } else if (second_num < 1) {
                throw new NumberValuesException("Second number is less than 1");
            } else {
                String operation = input.substring(input.indexOf(' ') + 1, input.lastIndexOf(' ')); // Смотрим какая операция
                int out_num = -1;
                switch (operation) //Выполнение операции
                {
                    case "+":
                        out_num = first_num + second_num;
                        break;
                    case "-":
                        out_num = first_num - second_num;
                        break;
                    case "*":
                        out_num = first_num * second_num;
                        break;
                    case "/":
                        out_num = first_num / second_num;
                }

                if (first_num_is_rome && second_num_is_rome) // Проверка на то римские ли числа даны на старте
                {
                    if (out_num < 1) // Проверка результата
                        throw new NumberValuesException("Result of rome expression can't be 0 or less");

                    System.out.println(ArabicToRome(out_num)); //Вывод римского числа
                } else
                    System.out.println(out_num); // Вывод арабского числа


            }
        }
    }

    private static int RomeToArabic(String Rome_number) {
        int Arabic_number = 0;

        while(!Rome_number.isEmpty()) {
            switch (Rome_number.substring(Rome_number.length() - 1)) {
                case "I":
                    ++Arabic_number;
                    Rome_number = Rome_number.substring(0, Rome_number.length() - 1);
                    break;
                case "V":
                    Arabic_number += 5;
                    Rome_number = Rome_number.substring(0, Rome_number.length() - 1);
                    break;
                case "X":
                    Arabic_number += 10;
                    Rome_number = Rome_number.substring(0, Rome_number.length() - 1);
                    break;
                default:
                    throw new NumberValuesException("This isn't rome number");
            }
        }

        return Arabic_number;
    } // Перевод в римские с арабских

    private static String ArabicToRome(int Arabic_number) {
        String Rome_number = "";

        while(Arabic_number > 0) {
            if (Arabic_number == 100) {
                Rome_number += "C";
                Arabic_number -= 100;
            }

            while(Arabic_number >= 50) {
                Rome_number +=  "L";
                Arabic_number -= 50;
            }

            while(Arabic_number >= 10) {
                Rome_number +=  "X";
                Arabic_number -= 10;
            }

            while(Arabic_number >= 5) {
                Rome_number += "V";
                Arabic_number -= 5;
            }

            while(Arabic_number > 0 && Arabic_number < 5) {
                Rome_number += "I";
                --Arabic_number;
            }
        }

        return Rome_number;
    }  // Перевод арабских чисел в римские

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String expression = sc.nextLine();
        int spaces = -2;
        String expcheck = expression;
        if (expcheck.indexOf(' ') == -1) {
            throw new ExpressionFormatException("It's not expression");
        } else
            while(expcheck.indexOf(' ') != -1){
                    expcheck = expcheck.substring(0, expcheck.lastIndexOf(' '));
                    spaces++;
                if(spaces>0)
                    throw new ExpressionFormatException("Expression isn't correct");
            }
            if(spaces==-1)
                throw new ExpressionFormatException("Expression isn't correct");
        String_calc(expression);
    }
}
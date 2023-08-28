import Exceptions.ExpressionFormatException;
import Exceptions.NumberValuesException;

import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void String_calc(String input) {
        boolean first_num_is_rome = false;
        boolean second_num_is_rome = false;
        String Rome_number;

        int first_num;
        int second_num;

        try {
            first_num = Integer.parseInt(input.substring(0, input.indexOf(32)));
        } catch (NumberFormatException var10) {
            Rome_number = var10.getMessage().substring(var10.getMessage().indexOf(34) + 1, var10.getMessage().lastIndexOf(34));
            first_num = RomeToArabic(Rome_number);
            first_num_is_rome = true;
        }

        if (first_num > 10) {
            throw new NumberValuesException("First number is greater than 10");
        } else if (first_num < 1) {
            throw new NumberValuesException("First number is less than 1");
        } else {

            try {
                second_num = Integer.parseInt(input.substring(input.lastIndexOf(32) + 1));
            } catch (NumberFormatException var9) {
                Rome_number = var9.getMessage().substring(var9.getMessage().indexOf(34) + 1, var9.getMessage().lastIndexOf(34));
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
                String operation = input.substring(input.indexOf(32) + 1, input.lastIndexOf(32));
                int out_num = -1;
                switch (operation) {
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

                if (first_num_is_rome && second_num_is_rome) {
                    if (out_num < 1) {
                        throw new NumberValuesException("Result of rome expression can't be 0 or less");
                    }

                    System.out.println(ArabicToRome(out_num));
                } else {
                    System.out.println(out_num);
                }

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
    }

    private static String ArabicToRome(int Arabic_number) {
        String Rome_number = "";

        while(Arabic_number > 0) {
            if (Arabic_number == 100) {
                Rome_number = Rome_number + "C";
                Arabic_number -= 100;
            }

            while(Arabic_number >= 50) {
                Rome_number = Rome_number + "L";
                Arabic_number -= 50;
            }

            while(Arabic_number >= 10) {
                Rome_number = Rome_number + "X";
                Arabic_number -= 10;
            }

            while(Arabic_number >= 5) {
                Rome_number = Rome_number + "V";
                Arabic_number -= 5;
            }

            while(Arabic_number > 0 && Arabic_number < 5) {
                Rome_number = Rome_number + "I";
                --Arabic_number;
            }
        }

        return Rome_number;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String expression = sc.nextLine();
        int spaces = -2;
        String expcheck = expression;
        if (expression.indexOf(32) == -1) {
            throw new ExpressionFormatException("It's not expression");
        } else {
            do {
                if (expcheck.indexOf(32) == -1) {
                    String_calc(expression);
                    return;
                }

                expcheck = expcheck.substring(0, expcheck.lastIndexOf(32));
                ++spaces;
            } while(spaces <= 0);

            throw new ExpressionFormatException("Expression isn't correct");
        }
    }

}
import Exceptions.ExpressionFormatException;
import Exceptions.NumberValuesException;

import java.util.Scanner;
enum Rome_digits{

    I,V,X,L,C
}
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
        int counter = 0;
        char[] Rome_number_char_arr = Rome_number.toCharArray();
        for (int i =0;i<Rome_number.length();i++)
        {
            if(Rome_number.equals("II")||Rome_number.equals("III"))
                break;
            if(Rome_number_char_arr[i]=='V'||Rome_number_char_arr[i]=='X')
                break;
            if(Rome_number_char_arr[i] == 'I' && Rome_number_char_arr[i+1] =='I')
                throw new NumberValuesException("Wrong rome number");
        }
        while(!Rome_number.isEmpty()) {
            switch (Rome_number.substring(Rome_number.length() - 1)) {
                case "I":
                    if(counter == 5)
                    {
                        Arabic_number =4;
                        Rome_number = Rome_number.substring(0, Rome_number.length() - 1);
                        break;
                    }
                    if(counter == 10)
                    {
                        Arabic_number =9;
                        Rome_number = Rome_number.substring(0, Rome_number.length() - 1);
                        break;
                    }
                    ++Arabic_number;
                    Rome_number = Rome_number.substring(0, Rome_number.length() - 1);
                    counter = 1;
                    break;
                case "V":
                    Arabic_number += 5;
                    Rome_number = Rome_number.substring(0, Rome_number.length() - 1);
                    counter = 5;
                    break;
                case "X":

                    Arabic_number += 10;
                    Rome_number = Rome_number.substring(0, Rome_number.length() - 1);
                    counter = 10;
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
        char[] Rome_number_char_arr = Rome_number.toCharArray();
        if(Rome_number.length()==4) {
            if (Rome_number_char_arr[0] == Rome_number_char_arr[1] && Rome_number_char_arr[0] == Rome_number_char_arr[2] && Rome_number_char_arr[0] == Rome_number_char_arr[3]) {
             Rome_number=Rome_number.substring(1, 2);

                if (Rome_number_char_arr[0] == 'I')
                    Rome_number += "V";
                if (Rome_number_char_arr[0] == 'X')
                    Rome_number += "L";
            }
        }
        if(Rome_number.length()==5)
            if(Rome_number_char_arr[1]=='X' && Rome_number_char_arr[2]=='X' && Rome_number_char_arr[3]=='X' && Rome_number_char_arr[4]=='X')
            {
                Rome_number=Rome_number.substring(2,3);
                Rome_number+="C";
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
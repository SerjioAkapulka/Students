package main;

import main.exceptions.OperationMainException;
import main.reader.CustomReader;
import main.reader.CustomReaderImpl;
import main.writer.CustomWriter;
import main.writer.CustomWriterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String pathFile = "C:\\Users\\Sergey\\Desktop\\note.txt";

    public static void main(String[] args) {
        System.out.println("Если вы хотите использовать сохраненный файл введите 1, если вы хотите создать новый файл - введите 2");
        Scanner scanner = new Scanner(System.in);
        CustomReader customReader = new CustomReaderImpl();
        try {
            String input = scanner.nextLine();
            if (Integer.parseInt(input) == 1) {
                HashMap<String, List<Integer>> students = customReader.readFromFile(pathFile); // данные из файла записываются в мапу
                Menu menu = new Menu(students);
                operate(scanner, menu);
            } else if (Integer.parseInt(input) == 2) {
                operate(scanner, new Menu());
            } else throw new OperationMainException("Illegal input for file operation");
        } catch (NumberFormatException ex) {
            System.out.println("Illegal input for file operation");
        }
    }

    private static void operate(Scanner scanner, Menu menu) { // преамбула для интерфейса
        System.out.println("Введите количество операций, которое необходимо совершить");
        CustomWriter customWriter = new CustomWriterImpl();
        CustomService customService = new CustomService();
        int count = 0;
        try {
            count = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("Введите число от 1 до 100 включительно");
        }
        if (count <= 0 || count >= 100) {
            throw new OperationMainException("Кол-во операций должно быть больше 0 и меньше 100");
        }
        while (count > 0) {
            try {
                customService.getMenu(menu);                               //вызов интерфейса для пользователя
                count--;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Если вы хотите сохранить данные в файл, введите 1");
        String input = scanner.nextLine();
        if (Integer.parseInt(input) == 1) {
            try {
                customWriter.writeToFile(menu);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

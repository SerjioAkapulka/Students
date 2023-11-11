package main;

import main.exceptions.CustomException;
import main.exceptions.OperationMainException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String inputStudent = "Введите имя ученика и нажмите ввод";
    public static final String pathFile = "C:\\Users\\Sergey\\Desktop\\note.txt";
    public static final String DATA_SEPARATOR = " ";


    public static void main(String[] args) {
        System.out.println("Если вы хотите использовать сохраненный файл введите 1, если вы хотите создать новый файл - введите 2");
        Scanner scanner = new Scanner(System.in);
        try {
            String input = scanner.nextLine();
            if (Integer.parseInt(input) == 1) {
                HashMap<String, List<Integer>> students = readFromFile(); // данные из файла записываются в мапу
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
        int count = 0;
        try {
            count = Integer.parseInt(scanner.nextLine());
        } catch (Exception ex) {
            System.out.println("Введите число от 1 до 100 включительно");
        }
            if (count <= 0 || count >= 100) {
                throw new OperationMainException("Кол-во операций должно быть больше 0 и меньше 100");
            }
        while (count > 0) {
            try {
                getMenu(menu);                               //вызов интерфейса для пользователя
                count--;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Если вы хотите сохранить данные в файл, введите 1");
        String input = scanner.nextLine();
        if (Integer.parseInt(input) == 1) {
            try {
                writeToFile(menu);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void writeToFile(Menu menu) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Main.pathFile), StandardCharsets.UTF_8))) {
            menu.students.forEach((key, value) -> {
                try {
                    writer.write(key + DATA_SEPARATOR + value + System.lineSeparator());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.flush();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static HashMap<String, List<Integer>> readFromFile() {
        HashMap<String, List<Integer>> students = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathFile))) {
            String line = reader.readLine();
            while (line != null) {
                line = line.replaceAll("\\[(.*?)\\]", "$1");
                String[] words = line.split(" ");
                String[] numbers = (words[1].split(","));
                for (String s : numbers) {
                    int num = Integer.parseInt(s);
                    list.add(num);
                }
                students.put(words[0], list);
                list = new ArrayList<>();
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    private static void getMenu(Menu menu) throws Exception {
        System.out.println("1 - добавить нового ученика");
        System.out.println("2 - удалить ученика");
        System.out.println("3 - обновить оценку ученика");
        System.out.println("4 - просмотреть оценки всех учащихся");
        System.out.println("5 - просмотреть оценки конкретного учащегося");
        int option = 0;
        Scanner scanner = new Scanner(System.in);
        try {
            String input = scanner.nextLine();
            option = Integer.parseInt(input);
        } catch (Exception ex) {
            System.out.println("Введите число от 1 до 5 включительно");
        }
        switch (option) {
            case 1 -> {
                String name = getStudentName(scanner, menu);
                List<Integer> list = getStudentsGrades(scanner, menu);
                menu.addStudent(name, list);
                System.out.println("Student was added");
            }
            case 2 -> {
                String name = getStudentName(scanner, menu);
                menu.removeStudent(name);
                System.out.println("Student was deleted or never existed");
            }
            case 3 -> {
                String name = getStudentName(scanner, menu);
                List<Integer> list = getStudentsGrades(scanner, menu);
                menu.renewStudent(name, list);
                System.out.println("Student was updated");
            }
            case 4 -> System.out.println(menu.browseAllStudents());
            case 5 -> {
                String name = getStudentName(scanner, menu);
                System.out.println(menu.browseStudent(name));
            }
            default -> throw new OperationMainException("Illegal option");
        }
    }

    private static List<Integer> getStudentsGrades(Scanner scanner, Menu menu) throws CustomException {
        System.out.println("Введите оценки ученика без пробелов через запятую");
        String inputStudentGrades = scanner.nextLine();
        return menu.processStudentGrades(inputStudentGrades);
    }

    private static String getStudentName(Scanner scanner, Menu menu) throws CustomException {
        System.out.println(inputStudent);
        String inputStudent = scanner.nextLine();
        return menu.processStudentName(inputStudent);
    }
}

package main;

import main.exceptions.OperationMainException;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static final Menu menu = new Menu();
    public static final String inputStudent = "Введите имя ученика и нажмите ввод";


    public static void main(String[] args) {
        System.out.println("Если вы хотите использовать сохраненный файл введите 1, если вы хотите создать новый файл - введите 2");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (Integer.parseInt(input) == 1) {
            getDatafromFile("pathToFile");
            getMenuFromFile();
        } else if (Integer.parseInt(input) == 2) {
            createNewFile("pathToFile");
            System.out.println("Введите количество операций, которое необходимо совершить");
            int count = 0;
            try {
                count = Integer.parseInt(scanner.nextLine());
                if (count <= 0) {
                    throw new OperationMainException("Кол-во операций должно быть больше 0");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            while (count > 0) {
                try {
                    getMenu();
                    count--;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } else throw new OperationMainException("Illegal input for file operation");
    }

    private static void getMenuFromFile() {
    }

    private static void getDatafromFile(String pathToFile) {
    }

    private static void createNewFile(String pathToFile) {
    }

    private static void getMenu() throws Exception {
        System.out.println("1 - добавить нового ученика");
        System.out.println("2 - удалить ученика");
        System.out.println("3 - обновить оценку ученика");
        System.out.println("4 - просмотреть оценки всех учащихся");
        System.out.println("5 - просмотреть оценки конкретного учащегося");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int option = Integer.parseInt(input);
        switch (option) {
            case 1 -> {
                String name = getStudentName(scanner);
                List<Integer> list = getStudentsGrades(scanner);
                menu.addStudent(name, list);
                System.out.println("Student was added");
            }
            case 2 -> {
                String name = getStudentName(scanner);
                menu.removeStudent(name);
                System.out.println("Student was deleted or never existed");
            }
            case 3 -> {
                String name = getStudentName(scanner);
                List<Integer> list = getStudentsGrades(scanner);
                menu.renewStudent(name, list);
                System.out.println("Student was updated");
            }
            case 4 -> System.out.println(menu.browseAllStudents());
            case 5 -> {
                String name = getStudentName(scanner);
                System.out.println(menu.browseStudent(name));
            }
            default -> throw new OperationMainException("Illegal option");
        }
    }

    private static List<Integer> getStudentsGrades(Scanner scanner) throws Exception {
        System.out.println("Введите оценки ученика без пробелов через запятую");
        String inputStudentGrades = scanner.nextLine();
        return menu.processStudentGrades(inputStudentGrades);
    }

    private static String getStudentName(Scanner scanner) throws Exception {
        System.out.println(inputStudent);
        String inputStudent = scanner.nextLine();
        return menu.processStudentName(inputStudent);
    }
}

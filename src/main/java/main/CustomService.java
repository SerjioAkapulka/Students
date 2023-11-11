package main;

import main.exceptions.CustomException;
import main.exceptions.OperationMainException;

import java.util.List;
import java.util.Scanner;

public class CustomService {

    public static final String inputStudent = "Введите имя ученика и нажмите ввод";

    public void getMenu(Menu menu) throws Exception {
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

    public List<Integer> getStudentsGrades(Scanner scanner, Menu menu) throws CustomException {
        System.out.println("Введите оценки ученика без пробелов через запятую");
        String inputStudentGrades = scanner.nextLine();
        return menu.processStudentGrades(inputStudentGrades);
    }

    public String getStudentName(Scanner scanner, Menu menu) throws CustomException {
        System.out.println(inputStudent);
        String inputStudent = scanner.nextLine();
        return menu.processStudentName(inputStudent);
    }
}

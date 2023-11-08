package main;

import main.exceptions.CustomException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Menu {

    Map<String, List<Integer>> students = new HashMap<>();
    String namePattern = "^[\\p{L} .'-]+$";

    public void removeStudent(String name) {
        students.entrySet().removeIf(entry -> entry.getKey().equals(name));
    }

    public void renewStudent(String name, List<Integer> list) {
        students.put(name, list);              //обновляем оценку в случае, если такой ученик есть, добавляем ученика и его оценки, если его нет
    }

    public Map<String, List<Integer>> browseAllStudents() {
        return students;
    }

    public List<Integer> browseStudent(String name) throws CustomException {
        if (students.get(name) != null)
            return students.get(name);
        else throw new CustomException("No such student");
    }

    public String processStudentName(String input3) throws CustomException {
        if (!Pattern.matches(namePattern, input3)) {
            throw new CustomException("Not valid input for name");
        }
        return input3;
    }

    public List<Integer> processStudentGrades(String input4) throws CustomException {
        ArrayList<Integer> list = new ArrayList<>();
        String[] words = input4.split(",");
        for (String gradeWord : words) {
            int grade = Integer.parseInt(gradeWord);
            if (grade >= 2 && grade <= 5)
                list.add(grade);
            else throw new CustomException("grades must be 2,3,4 or 5");
        }
        return list;
    }

    public void addStudent(String name, List<Integer> list) {
        students.put(name, list);
    }
}

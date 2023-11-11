package main.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomReaderImpl implements CustomReader {
    @Override
    public HashMap<String, List<Integer>> readFromFile(String pathFile) {
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
}

package main.reader;

import java.util.HashMap;
import java.util.List;

public interface CustomReader {

    HashMap<String, List<Integer>> readFromFile(String pathFile);
}

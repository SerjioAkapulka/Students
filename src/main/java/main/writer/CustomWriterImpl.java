package main.writer;

import main.Main;
import main.Menu;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CustomWriterImpl implements CustomWriter {
    public static final String DATA_SEPARATOR = " ";

    @Override
    public void writeToFile(Menu menu) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Main.pathFile), StandardCharsets.UTF_8))) {
            menu.getStudents().forEach((key, value) -> {
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
}

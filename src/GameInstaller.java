import java.io.*;
import java.util.*;

public class GameInstaller {
    private static final StringBuilder log = new StringBuilder();
    private static final List<String> directories = Arrays.asList(
            "C:/Games",
            "C:/Games/src",
            "C:/Games/res",
            "C:/Games/savegames",
            "C:/Games/temp",
            "C:/Games/src/main",
            "C:/Games/src/test",
            "C:/Games/res/drawables",
            "C:/Games/res/vectors",
            "C:/Games/res/icons"
    );

    private static final List<String> files = Arrays.asList(
            "C:/Games/src/main/Main.java",
            "C:/Games/src/main/Utils.java",
            "C:/Games/temp/temp.txt"
    );

    public static void main(String[] args) {
        createDirectories();
        createFiles();
        writeLog();
    }

    private static void createDirectories() {
        for (String path : directories) {
            File dir = new File(path);
            boolean created = dir.mkdirs();
            log.append(created ? "Успешно создана директория: " : "Ошибка создания директории: ")
                    .append(path).append("\n");
        }
    }

    private static void createFiles() {
        for (String path : files) {
            try {
                boolean created = new File(path).createNewFile();
                log.append(created ? "Успешно создан файл: " : "Ошибка создания файла: ")
                        .append(path).append("\n");
            } catch (IOException e) {
                log.append("Ошибка при создании файла ").append(path)
                        .append(": ").append(e.getMessage()).append("\n");
            }
        }
    }

    private static void writeLog() {
        String logPath = "C:/Games/temp/temp.txt";
        try (FileWriter writer = new FileWriter(logPath)) {
            writer.write(log.toString());
            System.out.println("Лог успешно записан в: " + logPath);
        } catch (IOException e) {
            System.out.println("Ошибка записи лога: " + e.getMessage());
        }
    }
}
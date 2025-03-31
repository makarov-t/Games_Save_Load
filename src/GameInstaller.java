import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameInstaller {
    public static void main(String[] args) {
        StringBuilder log = new StringBuilder();

        // Создаем базовую папку Games (если её нет)
        File gamesDir = new File("C://Games");
        if (!gamesDir.exists()) {
            if (gamesDir.mkdir()) {
                log.append("Директория Games создана успешно\n");
            } else {
                log.append("Ошибка при создании директории Games\n");
            }
        }

        File[] subDirs = {
                new File("C://Games/src"),
                new File("C://Games/res"),
                new File("C://Games/savegames"),
                new File("C://Games/temp")
        };

        for (File dir : subDirs) {
            if (dir.mkdir()) {
                log.append("Директория " + dir.getName() + " создана успешно\n");
            } else {
                log.append("Ошибка при создании директории " + dir.getName() + "\n");
            }
        }

        File srcMainDir = new File("C://Games/src/main");
        File srcTestDir = new File("C://Games/src/test");

        if (srcMainDir.mkdir()) {
            log.append("Директория main создана успешно\n");
        } else {
            log.append("Ошибка при создании директории main\n");
        }

        if (srcTestDir.mkdir()) {
            log.append("Директория test создана успешно\n");
        } else {
            log.append("Ошибка при создании директории test\n");
        }

        File mainJavaFile = new File("C://Games/src/main/Main.java");
        File utilsJavaFile = new File("C://Games/src/main/Utils.java");

        try {
            if (mainJavaFile.createNewFile()) {
                log.append("Файл Main.java создан успешно\n");
            } else {
                log.append("Ошибка при создании файла Main.java\n");
            }

            if (utilsJavaFile.createNewFile()) {
                log.append("Файл Utils.java создан успешно\n");
            } else {
                log.append("Ошибка при создании файла Utils.java\n");
            }
        } catch (IOException e) {
            log.append("Ошибка при создании файлов: " + e.getMessage() + "\n");
        }

        File[] resSubDirs = {
                new File("C://Games/res/drawables"),
                new File("C://Games/res/vectors"),
                new File("C://Games/res/icons")
        };

        for (File dir : resSubDirs) {
            if (dir.mkdir()) {
                log.append("Директория " + dir.getName() + " создана успешно\n");
            } else {
                log.append("Ошибка при создании директории " + dir.getName() + "\n");
            }
        }

        File tempFile = new File("C://Games/temp/temp.txt");
        try {
            if (tempFile.createNewFile()) {
                log.append("Файл temp.txt создан успешно\n");
            } else {
                log.append("Ошибка при создании файла temp.txt\n");
            }

            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(log.toString());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом temp.txt: " + e.getMessage());
        }

        System.out.println("Установка завершена. Лог записан в temp.txt");
    }
}
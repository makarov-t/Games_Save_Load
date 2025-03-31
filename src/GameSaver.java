import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GameSaver {
    public static void main(String[] args) {

        String basePath = "C:" + File.separator + "Games" + File.separator;
        String savePath = basePath + "savegames" + File.separator;

        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            if (saveDir.mkdirs()) {
                System.out.println("Создана папка для сохранений: " + savePath);
            } else {
                System.out.println("ОШИБКА: Не удалось создать папку для сохранений!");
                return;
            }
        }

        GameProgress progress1 = new GameProgress(100, 3, 1, 0.0);
        GameProgress progress2 = new GameProgress(85, 5, 2, 125.5);
        GameProgress progress3 = new GameProgress(50, 10, 5, 500.8);

        List<String> filesToZip = new ArrayList<>();
        saveGame(savePath + "save1.dat", progress1, filesToZip);
        saveGame(savePath + "save2.dat", progress2, filesToZip);
        saveGame(savePath + "save3.dat", progress3, filesToZip);

        if (filesToZip.isEmpty()) {
            System.out.println("Нет файлов для архивации!");
            return;
        }

        zipFiles(savePath + "saves.zip", filesToZip);

        deleteFiles(filesToZip);
    }

    public static void saveGame(String filePath, GameProgress progress, List<String> filesList) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Создан файл: " + filePath);
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(progress);
                filesList.add(filePath);
                System.out.println("Успешно сохранено в: " + filePath);
            }
        } catch (IOException e) {
            System.out.println("ОШИБКА сохранения в " + filePath + ": " + e.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> files) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String filePath : files) {
                File file = new File(filePath);
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zos.putNextEntry(entry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                    System.out.println("Добавлен в архив: " + file.getName());
                }
            }
        } catch (IOException e) {
            System.out.println("ОШИБКА архивации: " + e.getMessage());
        }
    }

    public static void deleteFiles(List<String> files) {
        for (String filePath : files) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Удален файл: " + file.getName());
            } else {
                System.out.println("Не удалось удалить: " + file.getName());
            }
        }
    }

}


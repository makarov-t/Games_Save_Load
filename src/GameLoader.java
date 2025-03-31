import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.*;

public class GameLoader {
    public static void main(String[] args) {
        String saveDir = "C:" + File.separator + "Games" + File.separator + "savegames" + File.separator;

        String zipPath = saveDir + "saves.zip";
        if (new File(zipPath).exists()) {
            openZip(zipPath, saveDir);
        }

        List<File> saveFiles = getSaveFiles(saveDir);
        if (saveFiles.isEmpty()) {
            System.out.println("Сохранения не найдены в папке: " + saveDir);
            return;
        }

        showMenu(saveFiles);

        File selectedFile = selectSaveFile(saveFiles);
        GameProgress progress = openProgress(selectedFile.getPath());

        System.out.println("\n=== Результаты сохранения ===");
        System.out.println("Файл: " + selectedFile.getName());
        System.out.println("Данные: " + progress);
    }

    private static List<File> getSaveFiles(String dirPath) {
        try {
            return Files.list(Paths.get(dirPath))
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".dat"))
                    .map(Path::toFile)
                    .sorted(Comparator.comparingLong(File::lastModified).reversed())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Ошибка чтения папки: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private static void showMenu(List<File> files) {
        System.out.println("\nДоступные сохранения:");
        for (int i = 0; i < files.size(); i++) {
            System.out.printf("%d. %s (изменен: %tc)%n",
                    i + 1,
                    files.get(i).getName(),
                    files.get(i).lastModified());
        }
    }

    private static File selectSaveFile(List<File> files) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nВыберите сохранение (1-" + files.size() + "): ");
            try {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= files.size()) {
                    return files.get(choice - 1);
                }
                System.out.println("Некорректный номер!");
            } catch (InputMismatchException e) {
                System.out.println("Введите число!");
                scanner.next();
            }
        }
    }

    private static void openZip(String zipPath, String unpackDir) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path filePath = Paths.get(unpackDir, entry.getName());
                Files.copy(zis, filePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Распакован: " + entry.getName());
            }
        } catch (IOException e) {
            System.out.println("Ошибка распаковки: " + e.getMessage());
        }
    }

    private static GameProgress openProgress(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (GameProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
            return null;
        }
    }
}


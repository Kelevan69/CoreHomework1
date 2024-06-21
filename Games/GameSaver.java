package Games;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GameSaver {

    public static void saveGame(String filePath, GameProgress gameProgress) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(gameProgress);
        oos.flush();
        oos.close();
        fos.close();
    }

    public static void zipFiles(String zipFilePath, List<String> filePaths) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String filePath : filePaths) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry zipEntry = new ZipEntry(filePath.substring(filePath.lastIndexOf("/") + 1));
                    zos.putNextEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
        }
    }

    public static void deleteFiles(List<String> filePaths) {
        for (String filePath : filePaths) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void main(String[] args) {

        GameProgress gameProgress1 = new GameProgress(100, 1, 1, 100.0);
        GameProgress gameProgress2 = new GameProgress(200, 2, 2, 200.0);
        GameProgress gameProgress3 = new GameProgress(300, 3, 3, 300.0);

        // Смените путь в дерикторию
        try {
            saveGame("C:/JAVA/Homework/Games/savegames/save1.dat", gameProgress1);
            saveGame("C:/JAVA/Homework/Games/savegames/save2.dat", gameProgress2);
            saveGame("C:/JAVA/Homework/Games/savegames/save3.dat", gameProgress3);

            List<String> filePaths = List.of(
                    "C:/JAVA/Homework/Games/savegames/save1.dat",
                    "C:/JAVA/Homework/Games/savegames/save2.dat",
                    "C:/JAVA/Homework/Games/savegames/save3.dat"
            );

            zipFiles("C:/JAVA/Homework/Games/savegames/savegames.zip", filePaths);
            deleteFiles(filePaths);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
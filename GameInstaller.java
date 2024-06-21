import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class GameInstaller {
    private static final Logger LOGGER = Logger.getLogger(GameInstaller.class.getName());

    public static void installGame(String gamesDirectoryPath) throws IOException {
        StringBuilder log = new StringBuilder();
        File gamesDirectory = new File(gamesDirectoryPath);

        createDirectory("src", gamesDirectoryPath);
        createDirectory("res", gamesDirectoryPath);
        createDirectory("savegames", gamesDirectoryPath);
        createDirectory("temp", gamesDirectoryPath);

        File tempFile = new File(gamesDirectoryPath + File.separator + "temp.txt");
        if (!tempFile.exists()) {
            if (tempFile.createNewFile()) {
                log.append("Файл temp.txt создан.\n");
            } else {
                log.append("Невозможно устнановить temp.txt.\n");
            }
        } else {
            log.append("temp.txt уже установлен.\n");
        }

        createFile("Main.java", gamesDirectoryPath + File.separator + "src" + File.separator + "main");
        createFile("Utils.java", gamesDirectoryPath + File.separator + "src" + File.separator + "main");

        createDirectory("drawables", gamesDirectoryPath + File.separator + "res");
        createDirectory("vectors", gamesDirectoryPath + File.separator + "res");
        createDirectory("icons", gamesDirectoryPath + File.separator + "res");

        FileWriter fileWriter = new FileWriter(tempFile);
        fileWriter.write(log.toString());
        fileWriter.close();
    }

    private static void createDirectory(String directoryName, String parentDirectoryPath) {
        File directory = new File(parentDirectoryPath + File.separator + directoryName);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                LOGGER.severe("Папку " + directoryName + " невозможно создать.");
            }
        }
    }

    private static void createFile(String fileName, String directoryPath) throws IOException {
        File file = new File(directoryPath + File.separator + fileName);
        if (!file.exists()) {
            if (file.createNewFile()) {
                LOGGER.info(fileName + " успешно установлен.");
            } else {
                LOGGER.severe(fileName + " уже существует.");
            }
        }
    }

    public static void main(String[] args) {
        try {
            installGame("C:/JAVA/Homework/Games"); // Замените на свой путь к папке Games
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
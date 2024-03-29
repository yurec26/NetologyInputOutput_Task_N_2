import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gm1 = new GameProgress(26, 26, 26, 26.0);
        GameProgress gm2 = new GameProgress(14, 24, 34, 44.4);
        GameProgress gm3 = new GameProgress(111, 222, 777, 999.9);
        //
        gm1.saveGame("C:/Users/Юрий/Desktop/GamesNetology/savegames/save1.dat");
        gm2.saveGame("C:/Users/Юрий/Desktop/GamesNetology/savegames/save2.dat");
        gm3.saveGame("C:/Users/Юрий/Desktop/GamesNetology/savegames/save3.dat");
        //
        zipFiles("C:/Users/Юрий/Desktop/GamesNetology/savegames/saves_Game.zip", Arrays.asList(
                gm1.getPath(),
                gm2.getPath(),
                gm3.getPath()
        ));
        //
        Consumer<GameProgress> cs = (gm -> {
            try {
                Files.delete(Paths.get(gm.getPath()));
            } catch (IOException e) {
                System.out.println(e);
            }
        });
        //
        cs.accept(gm1);
        cs.accept(gm2);
        cs.accept(gm3);
    }

    public static void zipFiles(String path, List<String> listFiles) {
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream(path))) {
            for (String s : listFiles) {
                try {
                    FileInputStream fis = new FileInputStream(s);
                    ZipEntry entry = new ZipEntry("save" + (listFiles.indexOf(s) + 1) + ".dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    fis.close();
                } catch (Exception ect) {
                    System.out.println(ect.getMessage());
                }
            }
            zout.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

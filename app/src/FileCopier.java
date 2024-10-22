import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FileCopier {
    public static String copy(String path){
        try {
            File file = new File(path);

            // Generate UUID for the file
            String uuid = UUID.randomUUID().toString();

            File destinationFile = new File("resources");
            String destinationPath = null;

            if (System.getProperty("os.name").contains("Windows"))
                destinationPath = destinationFile.getAbsolutePath() + "\\" + uuid;
            else
                destinationPath = destinationFile.getAbsolutePath() + "/" + uuid;


            // Create resources directory if not exists in the root directory of the project
            if (!destinationFile.exists())
                destinationFile.mkdir();

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(destinationPath);

            // Copy the file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fis.close();
            fos.close();

            return destinationPath;
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}

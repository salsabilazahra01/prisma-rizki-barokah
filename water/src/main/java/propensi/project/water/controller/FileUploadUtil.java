package propensi.project.water.controller;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        InputStream inputStream = multipartFile.getInputStream();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

    }

    public static void deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFolder(file);
                }
            }
        }
        folder.delete();
    }

    public static byte[] encodePicture(MultipartFile input) {
        String base64String = convertMultipartFileToBase64(input);
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        return decodedBytes;
    }

    public static String decodePicture(String encodedString){
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }

    private static String convertMultipartFileToBase64(MultipartFile multipartFile) {
        try {
            byte[] fileContent = multipartFile.getBytes();
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


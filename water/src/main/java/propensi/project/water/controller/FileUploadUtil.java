package propensi.project.water.controller;

import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

public class FileUploadUtil {

    public static Blob encodePicture(MultipartFile input) throws SQLException {
        String base64String = convertMultipartFileToBase64(input);
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        Blob blob = new SerialBlob(decodedBytes);
        return blob;
    }

    public static String decodePicture(Blob input){
        try {
            if (input != null) {
                // Convert Blob to byte array
                byte[] imageData = readBlob(input);

                // Convert byte array to Base64 string
                String base64Image = Base64.getEncoder().encodeToString(imageData);
                return base64Image;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
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

    private static byte[] readBlob(Blob blob) throws SQLException, IOException {
        InputStream inputStream = blob.getBinaryStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        return outputStream.toByteArray();
    }

}



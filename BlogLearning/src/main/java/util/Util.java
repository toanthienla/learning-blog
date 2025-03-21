/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.google.common.hash.Hashing;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus
 */
public class Util {

    /**
     * Method for getting the SHA256 hash of a String.
     *
     * @param str - The original String
     * @return - Th hashed String
     */
    public static String hash(String str) {
        //Reason we don't use MessageDigest class is that it's not thread safe
        return Hashing.sha256().hashString(str, StandardCharsets.UTF_8).toString();
    }

    /**
     * Helper method to log error into [ROOT_DIRECTORY]/logs/error.log.
     *
     * @param message - String message
     */
    public static void logError(String message) {
        try {
            File file = new File("logs/error.log"); // Write to a "logs" folder in the project root
            // Ensure directory exists
            file.getParentFile().mkdirs();

            //Create file writer 
            FileWriter writer;
            writer = new FileWriter(file, true); //true means the message will be appended instead of rewrite
            writer.write(message + System.lineSeparator());
            writer.close();
        } catch (IOException ex) {
        }
    }

    /**
     * Helper method for getting the File located in resources folder
     *
     * @param fileName - String: file's name. For example, if you want to access
     * the file quiz.json located directly under resources, then pass
     * 'quiz.json' as parameter value.
     * @return - The java.io.File object corresponding to that file. Return null
     * if file not found (file name logically incorrect)
     * @throws URISyntaxException - Throws URISyntaxException if the file name
     * is invalid (file name syntactically incorrect)
     */
    public static File getResource(String fileName) throws URISyntaxException {
        //Format the file name
        fileName = "/" + fileName;
        URL url = Util.class.getResource(fileName);
        return new File(url.toURI());
    }

    public static String readFileFromResources(String fileName) {
        try {
            File file = getResource(fileName);
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    /**
     * Method for writing file (markdown file), to data folder. The folder is
     * located in working directory
     *
     * @param file - The java.io.File object of the file to be written
     * @throws IOException - Throws IOException if there is error writing to
     * file
     */
    public static void writeFile(File file) throws IOException {
        //Create data folder if not exists
        File folder = new File("material");
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }

        //Write file object to data folder
        File destFile = new File(folder, file.getName()); //Create a file in material folder, with the same file name
        try ( InputStream in = new FileInputStream(file);  OutputStream out = new FileOutputStream(destFile)) {
            byte[] buffer = new byte[1024]; //buffer for reading byte content in file
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) { //Continue reading until no byte can be read
                out.write(buffer, 0, bytesRead); //Write data to file
            }
        }
    }

    public static String readFile(String filePath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            //getResource();
            //logError("Hello world");
            File file = new File("D:\\FPT\\4th_term\\PRJ301\\JSP.pdf");
            writeFile(file);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

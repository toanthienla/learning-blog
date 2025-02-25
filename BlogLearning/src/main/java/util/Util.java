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
import java.io.IOException;
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

    public static void main(String[] args) {
        //getResource();
    }
}

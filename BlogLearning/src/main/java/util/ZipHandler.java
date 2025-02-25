/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Asus
 */
public class ZipHandler {

    //Refer to https://en.wikipedia.org/wiki/List_of_file_signatures for the constant value
    private static final byte[] ZIP_SIGNATURE = {0x50, 0x4B, 0x03, 0x04};

    /**
     * Helper method to check if the zip file is truly a zip file (not a file
     * with modified extension).
     *
     * @param magicBytes - The magic byte array of the file for checking
     * @return - Boolean value, true if this is a zip file, false otherwise
     */
    public static boolean IsZipSignature(byte[] magicBytes) {
        return magicBytes[0] == ZIP_SIGNATURE[0]
                && magicBytes[1] == ZIP_SIGNATURE[1]
                && magicBytes[2] == ZIP_SIGNATURE[2]
                && magicBytes[3] == ZIP_SIGNATURE[3];
    }

    public static void Extract(String zipPath, String outputDir) throws IOException {
        //Create the ouput directory for extracting
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        //Loop through each zip entry and write in content into outputDir
        try ( FileInputStream fis = new FileInputStream(zipPath);  ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry zipEntry; //Each entry (files, folders) inside the zip file
            String filePath; //The file path of each zip entry for writing
            byte[] buffer; //Buffer byte array for writing
            int len; //The number of byte had read
            while ((zipEntry = zis.getNextEntry()) != null) {
                //Construct the file path for each zip entry
                filePath = outputDir + "/" + zipEntry.getName();

                //Read entry and write to disk memory
                try ( FileOutputStream fos = new FileOutputStream(filePath)) {
                    buffer = new byte[1024]; //We limit each read to maximum of 1024 byte (1KB)
                    while ((len = zis.read(buffer)) > 0) { //If len = 0 -> End of file
                        fos.write(buffer, 0, len);
                    }
                }

                //Closing entry
                zis.closeEntry();
            }
        }
    }

    public static void main(String[] args) {

    }
}

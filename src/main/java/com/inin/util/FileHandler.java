package com.inin.util;

import java.io.File;
import java.io.IOException;

/**
 * Created by root on 11/2/16.
 */
public class FileHandler {
    /**
     * Create file if not present and return File object
     * @return File
     * @throws IOException
     */
    public static File createFile(String fileName) {
        File file = null;
        try {
            file = new File("src/main/resources/"+fileName);
            file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }
}

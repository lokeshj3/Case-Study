package com.helpdesk.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
    public static int readInteger(String message){
        System.out.println(message);
        int userInput = 0;
        try{
            //MD:You are not closing the resources
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            userInput = Integer.parseInt(consoleReader.readLine());
            //consoleReader.close();
        }
        catch (NumberFormatException ne) {
            System.out.println("Invalid integer Number!!!");
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
        return userInput;
    }

    public static String readString(String message){
        System.out.println(message);
        String userInput = "";
        try{
            //MD:You are not closing the resources
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            userInput = consoleReader.readLine();

        }catch(IOException ie) {
            ie.printStackTrace();
        }
        return userInput;
    }


    public static File createFile(String filePath, String fileName){
        File file = null;
        try {
            file = new File(filePath);
            if(!file.exists() || !file.isDirectory()){
                file.mkdir();
            }

            file= new File(filePath, fileName);
            if(!file.exists())
                file.createNewFile();

        }catch(IOException ie){
            ie.printStackTrace();
        }

        return file;
    }
}

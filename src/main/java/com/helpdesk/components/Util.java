package com.helpdesk.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
    public static int readInteger(){
        int userInput = 0;
        try{
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            userInput = Integer.parseInt(consoleReader.readLine());
            consoleReader.close();
        }
        catch (NumberFormatException ne) {
            System.out.println("Invalid integer Number!!!");
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
        return userInput;
    }

    public static String readString(){
        String userInput = null;
        try{
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            userInput = consoleReader.readLine();
            consoleReader.close();
        }catch(IOException ie) {
            ie.printStackTrace();
        }
        return userInput;
    }

    public static File createFile(String fileName){
        File newFile = null;
        try {
            newFile = new File("src/main/data/" + fileName);
            newFile.createNewFile();
        }catch(IOException ie){
            ie.printStackTrace();
        }
        return newFile;
    }
}

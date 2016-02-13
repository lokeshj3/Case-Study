package com.ticketmaster.utils;

import com.ticketmaster.models.Ticket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.EOFException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * SerializerUtil class
 *
 * Created by Virendra on 8/2/16.
 */

public class SerializerUtil {

    public SerializerUtil() {
    }

    public SerializerUtil(String filename){
        fileName = filename;
    }


    private void checkFiles(String filename) throws IOException {
        File directory = new File (base);
        if (!directory.exists() && !directory.isDirectory()){
            directory.mkdirs();
        }
        file = new File(base, filename);
        if (!file.exists()){
            file.createNewFile();
        }
    }

    private void connectWriter()
            throws IOException{

        if (file.length() <= 0){
            fOut = new ObjectOutputStream(new FileOutputStream(file));
        }else {
            fOut = new ObjectOutputStream(new FileOutputStream(file, true)) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
        }
    }

    private void disconnectWriter()
            throws IOException{
        fOut.close();
    }

    private void connectReader()
            throws IOException{
        fIn = new ObjectInputStream(new FileInputStream(file));
    }

    private void disconnectReader()
            throws IOException{
        fIn.close();
    }

    public void emptyObjectFile()
            throws IOException{

        if (file == null) checkFiles(SerializerUtil.getSerializedFileName());

        new ObjectOutputStream(new FileOutputStream(file)).close();
        new ObjectOutputStream(new FileOutputStream(file)).close();
    }

    /**
     *
     * @param objects
     * @throws IOException
     */
    public void writeToFile(Map<?, ?> objects) throws IOException{

        //check and setup file
        if (file == null) {
            checkFiles(fileName);
        }

        //connect write to file
        connectWriter();

        //take list from the supplied value
        List<?> tempList = new ArrayList<>(objects.values());

        //parse data and write to file
        if (tempList!= null){

            tempList.stream().forEach((e)->{
                try {
                    fOut.writeObject(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

        }

        //disconnect writer
        disconnectWriter();
    }

    public Map<?,?> readFromFile()
            throws IOException, ClassNotFoundException{

        checkFiles(fileName); // EB: Good practice to use 'this' keyword.
        boolean flag = true;


        Map<Integer, Ticket> tempMap = new HashMap<>();

        if (file.length() !=0){
            connectReader();
            try{

                while(flag){
                    //update: noted
                    Ticket temp = (Ticket) fIn.readObject();
                    tempMap.put(new Integer(temp.getId()), temp);
                }
            }catch (EOFException e){
                CustomLogger.init(SerializerUtil.class).warn("reached end of file while reading objects");

            }
            disconnectReader();
        }
        return tempMap;
    }

    public void writeProperty(String key , String value)
            throws IOException {

        Properties prop = loadPropertiesFile();

        FileOutputStream fOut = new FileOutputStream(base+File.separator+propertyFile);

        prop.setProperty(key, value);
        prop.store(fOut,null);

    }

    public Properties loadPropertiesFile()
            throws IOException {
        Properties prop = new Properties();

        File tmpFile = new File(base+ File.separator+propertyFile);
        if (!tmpFile.exists()){
            checkFiles(propertyFile);
        }

        FileInputStream in = new FileInputStream(base+ File.separator+propertyFile);
        prop.load(in);

        return prop;
    }

    public String readProperty(String key)
            throws IOException{
        //add or read properties for id of ticket list
        Properties prop = loadPropertiesFile();

        if (prop.getProperty(key) == null){
            //no value found, initial id
            writeProperty(key,"1");
            prop = loadPropertiesFile();
        }

        return prop.getProperty(key);

    }


    public static String getSerializedFileName(){
        return "tickets.ser";
    }


    //properties
    final String base = "files";
    String propertyFile = "conf.properties";
    String fileName = SerializerUtil.getSerializedFileName();
    File file;
    ObjectOutputStream fOut;
    ObjectInputStream fIn;
}

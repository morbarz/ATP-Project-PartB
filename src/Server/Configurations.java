package Server;

import java.io.*;
import java.util.Properties;

public class Configurations {
    private static Configurations properties_file = null;
    private Properties properties;
    private InputStream inStream;
    private OutputStream outStream;



    private Configurations(){}

    public static Configurations createInstance(){
        if (properties_file != null){
            System.out.println("Properties File already exist");
        }
        else {
            properties_file = new Configurations();
        }
        return properties_file;
    }


    public void creatPropFile(){
        try{
            inStream = getClass().getClassLoader().getResourceAsStream("config.properties");
            properties = new Properties();

            outStream = new FileOutputStream("resources/config.properties");
            properties.load(inStream);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized Properties readConfig(){
        try {
            properties = new Properties();
            inStream = new FileInputStream("resources\\config.properties");
            properties.load(inStream);
            return properties;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

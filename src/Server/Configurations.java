package Server;

import java.io.*;
import java.util.Properties;

public class Configurations {
    private static Configurations prop_file = null;
    private Properties properties;
    private InputStream inStream;
    private OutputStream outStream;



    private Configurations(){}

    public static Configurations createInstance(){
        if (prop_file != null){
            System.out.println("Properties File already exist");
        }
        else {
            prop_file = new Configurations();
        }
        return prop_file;
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

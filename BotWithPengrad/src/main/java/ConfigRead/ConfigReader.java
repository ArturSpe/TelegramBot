package ConfigRead;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private String password;
    private String user;
    private String urlStartDb;
    private String driver;

    public ConfigReader(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\ArturAsr\\Desktop\\configTgBotEcho.properties"));
            Properties properties = new Properties();
            properties.load(reader);

            password = properties.getProperty("PASSWORD");
            user = properties.getProperty("USER_DATABASE");
            urlStartDb = properties.getProperty("DB_START_URL");
            driver = properties.getProperty("DRIVER");
        }catch (Exception e){e.printStackTrace();}
    }

    public String getPassword() {
        return password;
    }
    public String getUser() {
        return user;
    }
    public String getUrlStartDb() {
        return urlStartDb;
    }
    public String getDriver() {
        return driver;
    }
}

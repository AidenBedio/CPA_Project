package DBWindow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionConfiguration {
    public static Connection getConnection(){
        Connection connection = null;

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "80396");
        properties.setProperty("useSSL", "false");
        properties.setProperty("autoReconnect", "true");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/datab", properties);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}

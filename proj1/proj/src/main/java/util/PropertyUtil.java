package util;
//
//import java.io.InputStream;
//import java.util.Properties;
//
//public class PropertyUtil {
//    // Static method to read connection properties and return the connection string
//    public static String getPropertyString() {
//        Properties properties = new Properties();
//        String connectionString = null;
//
//        try {
//            // Load the property file using the ClassLoader
//            ClassLoader loader = PropertyUtil.class.getClassLoader();
//            InputStream input = loader.getResourceAsStream("org/hexaware/util/db.properties");
//            
//            if (input == null) {
//                throw new RuntimeException("Unable to find db.properties");
//            }
//
//            // Load the properties from the file
//            properties.load(input);
//
//            // Read the connection properties from the file
//            String hostname = properties.getProperty("db.url");  // Now, using `db.url`
//            String username = properties.getProperty("db.username");
//            String password = properties.getProperty("db.password");
//
//            // Construct the connection string (assuming MySQL)
//            connectionString = hostname + "?user=" + username + "&password=" + password;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error reading property file.");
//        }
//
//        return connectionString;
//    }
//}


import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
    private static final String path = "db.properties";
    public static String getPropertyString() 
    {
    	 String connectionString = null;
        Properties properties = new Properties();
        try (InputStream inputStream = PropertyUtil.class.getResourceAsStream(path)) {
           
                properties.load(inputStream);
                connectionString = "jdbc:mysql://" +
                        properties.getProperty("hostname") +
                        ":" + properties.getProperty("port") +
                        "/" + properties.getProperty("dbname") +
                        "?user=" + properties.getProperty("username") +
                        "&password=" + properties.getProperty("password");
                return connectionString;
            } 
         catch (IOException e) {
            e.printStackTrace();
        
        }
		return connectionString;
		
    }
    
   
}

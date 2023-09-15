import java.sql.*;

public class JDBC {
    Connection connection;
    public void setConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String userName = "root";
            String password = "MySql";
            String url = "jdbc:mysql://localhost:2023/GroceryManagement";
            connection = DriverManager.getConnection(url, userName, password);
            // System.out.println("Connected To Data Base");
        } catch(Exception e) {
            System.out.println("Exception In Database Connection...");
        }
    }
}


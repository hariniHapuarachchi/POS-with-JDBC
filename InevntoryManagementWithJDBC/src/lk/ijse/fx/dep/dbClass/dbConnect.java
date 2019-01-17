package lk.ijse.fx.dep.dbClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnect {

    private static Connection connection;
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryInfo", "root", "root");
            System.out.println("Connected to database");
        }
        //System.out.println("Connected to database");

        return connection;
    }


}

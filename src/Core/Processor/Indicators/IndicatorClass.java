package Core.Processor.Indicators;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by ZachBluhm on 2/22/15.
 */
public class IndicatorClass {


    private static String MYSQL_CONNECTION_URL = "jdbc:mysql://localhost:3306/stockdb";
    private static String USER = "root";
    private static String PASSWORD = "";
    public String STOCK = "";

    public IndicatorClass(String stock) {
        this.STOCK = stock;

    }


    public static Connection getCon() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(MYSQL_CONNECTION_URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}

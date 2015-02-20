package MySQLCSVImporter;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

/**
 * Created by ZachBluhm on 2/19/15.
 */
public class Main {

    private static String MYSQL_CONNECTION_URL = "jdbc:mysql://localhost:3306/stockdb";
    private static String USER = "root";
    private static String PASSWORD = "";

    public static void main(String[]args) {

        try {
            //CSVLoader loader = new CSVLoader(getCon(), "AA");

            //loader.loadCSV("/Users/ZachBluhm/stock-tracker/Data/CSVs/AA.csv", "HistoricalPrices", true);

            CSVStockLoader loader = new CSVStockLoader(getCon());
            loader.loadCSV("/Users/ZachBluhm/stock-tracker/Data/stock_tickers1.txt", "Stocks", true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection getCon() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(MYSQL_CONNECTION_URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}

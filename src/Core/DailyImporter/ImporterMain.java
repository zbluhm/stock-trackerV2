package Core.DailyImporter;


import MySQLCSVImporter.CSVLoader;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

/**
 * Created by ZachBluhm on 2/20/15.
 */
public class ImporterMain {
    private static String MYSQL_CONNECTION_URL = "jdbc:mysql://localhost:3306/stockdb";
    private static String USER = "root";
    private static String PASSWORD = "";

    public static void main(String[]args) {

        CSVDownloader.run();


        List<String> lines;

        try {
            lines = FileUtils.readLines(new File("/Users/ZachBluhm/stock-tracker/Data/stock_tickers1.txt"));
            int left = 0;

            for (String line : lines) {
                try {
                    CSVLoader loader = new CSVLoader(getCon(), line);

                    loader.loadCSV("/Users/ZachBluhm/stock-tracker/Data/DailyCSVs/" + line + ".csv", "HistoricalPrices", false);

                    System.out.println(left + " done out of " + lines.size());

                    left++;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

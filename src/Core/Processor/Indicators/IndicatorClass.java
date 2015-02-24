package Core.Processor.Indicators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

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

    public float[] getStockData(int amount, int interval, String type) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        float[] data = new float[interval];
        Date[] dates = new Date[interval];
        int lastDataPos = 0;


        String query = "SELECT " + type + ", date FROM HistoricalPrices WHERE date BETWEEN DATE_SUB(NOW(), INTERVAL "+ interval+" DAY) AND now() AND symbol = '" + STOCK + "';";
        System.out.println(query);
        try {
            con = getCon();
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            int count = 0;

            while(rs.next()) {

                data[count] = rs.getFloat(type);
                dates[count] = rs.getDate("date");
                count++;

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0){
                break;
            }
            lastDataPos++;
        }

        float[] dataTrimmed = new float[amount];
        int startPos = lastDataPos - amount;
        if(data.length >= amount && startPos > 0) {
            for (int i = startPos, x = 0; x < dataTrimmed.length; x++, i++) {
                dataTrimmed[x] = data[i];
            }

            return dataTrimmed;
        }
        return null;
    }


}

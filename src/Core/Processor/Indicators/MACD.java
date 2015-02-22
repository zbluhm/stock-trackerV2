package Core.Processor.Indicators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZachBluhm on 2/22/15.
 */
public class MACD extends IndicatorClass implements Indicator {
    /**
     * EMA = Price(t) * k + EMA(y) * (1 – k)
     * t = today, y = yesterday, N = number of days in EMA, k = 2/(N+1)
     *
     */


    public MACD(String stock) {
        super(stock);
    }

    public float getK(int n) {  //where n is number of days
            return 2/(n+1);
    }

    public float calculateEMA(float todaysPrice, int numberOfDays, float EMAYesterday) {
        float k = getK(numberOfDays);


        return (todaysPrice*k) + (EMAYesterday * (1-k));

    }

    public float[] calculateTwentySixDayEMA() {
        float[] stockDataTwentySix = getStockData(26);
        float[] emaTwentySix = new float[26];


        for (int i = 0; i < 26; i++) {  //calculate the 26 day ema of each stock date
            if (i == 0) {
                emaTwentySix[i] = stockDataTwentySix[i];
            } else {
                emaTwentySix[i] = calculateEMA(stockDataTwentySix[i], 26, emaTwentySix[i-1]);
            }
        }

        return emaTwentySix;

    }

    public float[] calculateTwelveDayEMA() {
        float[] stockDataTwelve = getStockData(12);
        float[] emaTwelve = new float[12];


        for (int i = 0; i < 12; i++) {
            if (i == 0) {
                emaTwelve[i] = stockDataTwelve[i];
            } else {
                emaTwelve[i] = calculateEMA(stockDataTwelve[i], 12, emaTwelve[i-1]);
            }
        }

        return emaTwelve;

    }



    public float[] getStockData(int amount) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        float[] data = new float[50];
        Date[] dates = new Date[50];
        int lastDataPos = 0;


        String query = "SELECT close, date FROM HistoricalPrices WHERE date BETWEEN DATE_SUB(NOW(), INTERVAL 50 DAY) AND now() AND symbol = " + STOCK + ';';

        try {
            con = getCon();
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            int count = 0;

            while(rs.next()) {

                data[count] = rs.getFloat("close");
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

        for(int i = startPos, x = 0; x < dataTrimmed.length; x++, i++) {
            dataTrimmed[x] = data[i];
        }


        return dataTrimmed;
    }



    @Override
    public float returnIndication() {
        return 0;
    }


}

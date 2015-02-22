package Core.Processor.Indicators;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

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

    public void getEMA() {
        float[] stockDate = getStockData(40);

        double[] out = new double[40];

        MInteger begin = new MInteger();
        MInteger length = new MInteger();

        Core core = new Core();

        RetCode ret = core.ema(0, 39, stockDate, 26, begin, length, out);

        if (ret == RetCode.Success) {
            System.out.println("Output Begin:" + begin.value);
            System.out.println("Output Begin:" + length.value);
        }

        for (int i = begin.value; i < stockDate.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append("Period #");
            line.append(i+1);
            line.append(" close= ");
            line.append(stockDate[i]);
            line.append(" mov avg=");
            line.append(out[i-begin.value]);
            System.out.println(line.toString());
        }
    }

    public void getMACD() {
        float[] stockData = getStockData(40);

        double[] outMACD = new double[40];
        double[] outMACDSIgnal = new double[40];
        double[] outMACDHist = new double[40];

        MInteger begin = new MInteger();
        MInteger length = new MInteger();

        Core core = new Core();

        RetCode ret = core.macd(0, 39, stockData, 12, 26, 9, begin, length, outMACD, outMACDSIgnal, outMACDHist);

        if (ret == RetCode.Success) {
            System.out.println("Output Begin:" + begin.value);
            System.out.println("Output Begin:" + length.value);
        }

        for (int i = begin.value; i < stockData.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append("Period #");
            line.append(i+1);
            line.append(" close= ");
            line.append(stockData[i]);
            line.append(" macd=");
            line.append(outMACD[i-begin.value]);
            line.append(" signal=");
            line.append(outMACDSIgnal[i-begin.value]);
            System.out.println(line.toString());
        }
    }



    public float[] getStockData(int amount) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        float[] data = new float[70];
        Date[] dates = new Date[70];
        int lastDataPos = 0;


        String query = "SELECT close, date FROM HistoricalPrices WHERE date BETWEEN DATE_SUB(NOW(), INTERVAL 100 DAY) AND now() AND symbol = " + STOCK + ';';

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

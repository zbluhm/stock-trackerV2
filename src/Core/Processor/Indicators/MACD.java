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
    double[] signal = new double[150];
    double[] macd = new double[150];

    public MACD(String stock) {
        super(stock);
    }

    public void getMACD() {
        float[] stockData = getStockData(150, 250, "close");

        if(stockData == null) {
            signal = null;
            macd = null;
            return;
        }

        double[] outMACD = new double[150];
        double[] outMACDSIgnal = new double[150];
        double[] outMACDHist = new double[150];

        MInteger begin = new MInteger();
        MInteger length = new MInteger();

        Core core = new Core();

        RetCode ret = core.macd(0, 149, stockData, 12, 26, 9, begin, length, outMACD, outMACDSIgnal, outMACDHist);

        for(int i = 0; i < outMACDSIgnal.length; i++) {
            signal[i] = outMACDSIgnal[i];
            macd[i] = outMACD[i];
        }


    }

    public boolean hasConverged(double[] macd, double[] macdSignal) {
        boolean macdIsHigher = false;
        boolean hasConverged = false;

        if (macd[0] > macdSignal[0]) {
            macdIsHigher = true;
            return false;
        }

        int dayCrossed = 0;

        for (int i = 0; i < macd.length; i++) {

            if (macdSignal[i] > macd[i]) {
                hasConverged = true;
                dayCrossed = i + 1;
                return true;
            }

        }

        return false;

    }
    @Override
    public float returnIndication() {
        return 0;
    }

    public double[] getSignal() {
        return signal;
    }

    public double[] getMacd() {
        return macd;
    }
}

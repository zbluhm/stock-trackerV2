package Core.Processor.Indicators;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

/**
 * Created by ZachBluhm on 2/22/15.
 */
public class BollingerBands extends IndicatorClass implements Indicator {

    public BollingerBands(String stock) {
        super(stock);
    }

    public void getBollingerBands() {
        float[] stockData = getStockData(100, 175, "close");

        MInteger begin = new MInteger();
        MInteger end = new MInteger();

        double[] upperBand = new double[100];
        double[] middleBand = new double[100];
        double[] lowerBand = new double[100];

        Core core = new Core();

        RetCode ret = core.bbands(0, 99, stockData, 20, 2, 2, MAType.Sma, begin, end, upperBand, middleBand, lowerBand);

        for (int i = begin.value; i < stockData.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append("Period #");
            line.append(i+1);
            line.append(" close= ");
            line.append(stockData[i]);
            line.append(" upper=");
            line.append(upperBand[i-begin.value]);
            line.append(" middle=");
            line.append(middleBand[i-begin.value]);
            line.append(" lower=");
            line.append(lowerBand[i-begin.value]);
            System.out.println(line.toString());
        }

    }





    @Override
    public float returnIndication() {
        return 0;
    }
}

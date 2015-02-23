package Core.Processor.Indicators;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

/**
 * Created by ZachBluhm on 2/22/15.
 */
public class ADX extends IndicatorClass {

    public ADX(String stock) {
        super(stock);
    }

    public void getADX() {
        MInteger begin = new MInteger();
        MInteger end = new MInteger();

        float[] high = getStockData(100, 175, "high");
        float[] low = getStockData(100, 175, "low");
        float[] close = getStockData(100, 175, "close");

        double[] adx = new double[100];

        Core core = new Core();

        RetCode ret = core.adx(0, 99, high, low, close, 14, begin, end, adx);

        System.out.println(close.length);

        for (int i = begin.value; i < close.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append("Period #");
            line.append(i+1);
            line.append(" close= ");
            line.append(close[i]);
            line.append(" macd=");
            line.append(adx[i-begin.value]);
            line.append(" signal=");
            System.out.println(line);
        }



    }
}

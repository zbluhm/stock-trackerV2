package Core.Processor.Indicators;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

/**
 * Created by ZachBluhm on 2/23/15.
 */
public class WillR extends IndicatorClass {

    public WillR(String stock) {
        super(stock);
    }

    public void getWillR() {
        float[] high = getStockData(100, 175, "high");
        float[] low = getStockData(100, 175, "low");
        float[] close = getStockData(100, 175, "close");

        MInteger begin = new MInteger();
        MInteger end = new MInteger();

        double[] out = new double[100];

        Core core = new Core();

        RetCode ret = core.willR(0, 99, high, low, close, 14, begin, end, out);

        for (int i = begin.value; i < close.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append("Period #");
            line.append(i+1);
            line.append(" close= ");
            line.append(close[i]);
            line.append(" %r=");
            line.append(out[i-begin.value]);
            System.out.println(line.toString());
        }


    }
}

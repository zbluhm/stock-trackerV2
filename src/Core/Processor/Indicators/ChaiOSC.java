package Core.Processor.Indicators;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

/**
 * Created by ZachBluhm on 2/23/15.
 */
public class ChaiOSC extends IndicatorClass {

    public ChaiOSC(String stock) {
        super(stock);
    }

    public void getChaiOSC() {
        Core core = new Core();

        float[] high = getStockData(100, 175, "high");
        float[] low = getStockData(100, 175, "low");
        float[] close = getStockData(100, 175, "close");
        float[] volume = getStockData(100, 175, "volume");

        MInteger begin = new MInteger();
        MInteger end = new MInteger();

        double[] out = new double[100];

        RetCode ret = core.adOsc(0, 99, high, low, close, volume, 3, 10, begin, end, out);

        for (int i = begin.value; i < close.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append("Period #");
            line.append(i+1);
            line.append(" close= ");
            line.append(close[i]);
            line.append(" chai=");
            line.append(out[i-begin.value]);
            System.out.println(line.toString());
        }
    }

    public void getChaiLine() {
        Core core = new Core();

        float[] high = getStockData(100, 175, "high");
        float[] low = getStockData(100, 175, "low");
        float[] close = getStockData(100, 175, "close");
        float[] volume = getStockData(100, 175, "volume");

        MInteger begin = new MInteger();
        MInteger end = new MInteger();

        double[] out = new double[100];

        RetCode ret = core.ad(0, 99, high, low, close, volume, begin, end, out);

        for (int i = begin.value; i < close.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append("Period #");
            line.append(i+1);
            line.append(" close= ");
            line.append(close[i]);
            line.append(" chai=");
            line.append(out[i-begin.value]);
            System.out.println(line.toString());
        }

    }
}

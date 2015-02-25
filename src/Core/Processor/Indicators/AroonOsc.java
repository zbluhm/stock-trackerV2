package Core.Processor.Indicators;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

/**
 * Created by ZachBluhm on 2/23/15.
 */
public class AroonOsc extends IndicatorClass {

    /**
     * +50 means the stock is in a strong uptrend
     * -50 means the stock is in a strond downtrend
     *
     */

    double[] out = new double[100];

    public AroonOsc(String stock) {
        super(stock);
    }

    public void getAroonOsc() {
        float[] high = getStockData(100, 175, "high");
        float[] low = getStockData(100, 175, "low");

        MInteger begin = new MInteger();
        MInteger end = new MInteger();

        double[] out = new double[100];


        Core core = new Core();

        RetCode ret = core.aroonOsc(0, 99, high, low, 35, begin, end, out);

        System.out.println(begin.value);

        for (int i = begin.value; i < high.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append("Period #");
            line.append(i+1);
            line.append(" high= ");
            line.append(high[i]);
            line.append(" low=");
            line.append(low[i]);
            line.append(" aroon=");
            line.append(out[i-begin.value]);
            System.out.println(line);
        }

        System.arraycopy(out, 0, this.out, 0, out.length);
    }

    public double[] getOut() {
        return out;
    }

}

package Core.Processor.Indicators;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

/**
 * Created by ZachBluhm on 2/23/15.
 */
public class RSI extends IndicatorClass {

    double[] out = new double[150];

    public RSI (String stock) {
        super(stock);
    }


    public void getRSI() {
        float[] inData = getStockData(150, 250, "close");
        MInteger begin = new MInteger();
        MInteger end = new MInteger();

        double[] out = new double[150];


        Core core = new Core();

        RetCode ret = core.rsi(0, 149, inData, 14, begin, end, out);



        /*for (int i = begin.value; i < inData.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append("Period #");
            line.append(i+1);
            line.append(" close= ");
            line.append(inData[i]);
            line.append(" rsi=");
            line.append(out[i-begin.value]);
            System.out.println(line);
        }*/

        for(int i = 0; i < out.length; i++) {
            this.out[i] = out[i];

        }
    }

    public double[] getOut() {
        return out;
    }
}

package Core.Processor.Indicators;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;


/**
 * Created by ZachBluhm on 2/23/15.
 */
public class MAMA extends IndicatorClass {
    public MAMA(String stock) {
        super(stock);
    }

    public void getMAMA() {
        float[] close = getStockData(100, 175, "close");
        float fast = 0.5f;
        float slow = 0.05f;

        MInteger begin = new MInteger();
        MInteger end = new MInteger();

        double[] outMAMA = new double[100];
        double[] outFAMA = new double[100];

        Core core = new Core();

        RetCode ret = core.mama(0, 99, close, fast, slow, begin, end, outMAMA, outFAMA);

        for (int i = begin.value; i < close.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append("Period #");
            line.append(i+1);
            line.append(" close= ");
            line.append(close[i]);
            line.append(" MAMA=");
            line.append(outMAMA[i-begin.value]);
            line.append(" FAMA=");
            line.append(outFAMA[i-begin.value]);
            System.out.println(line.toString());
        }

    }
}

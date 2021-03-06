package Core.Processor.Indicators;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

/**
 * Created by ZachBluhm on 2/22/15.
 */
public class OBV extends IndicatorClass {
    public OBV(String stock) {
        super(stock);
    }

    public void getOBV() {
        float[] close = getStockData(100, 175, "close");
        float[] volume = getStockData(100, 175, "volume");

        MInteger begin = new MInteger();
        MInteger end = new MInteger();

        double[] out = new double[100];


        Core core = new Core();

        RetCode ret = core.obv(0, 99, close, volume, begin, end, out);

        for (int i = begin.value; i < close.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append("Period #");
            line.append(i+1);
            line.append(" close= ");
            line.append(close[i]);
            line.append((" volume= "));
            line.append(volume[i]);
            line.append(" OBV=");
            line.append(out[i-begin.value]);
            System.out.println(line.toString());
        }

    }
}

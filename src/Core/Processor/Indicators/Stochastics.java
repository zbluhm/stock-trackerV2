package Core.Processor.Indicators;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

/**
 * Created by ZachBluhm on 2/22/15.
 */
public class Stochastics extends IndicatorClass implements Indicator{

    public Stochastics(String stock) {
        super(stock);
    }


   public void getStochastics() {
       MInteger begin = new MInteger();
       MInteger end = new MInteger();

       int fastk = 14;
       int slowk = 3;
       int slowd = 3;

       float[] stockCloseData = getStockData(100, 200, "close");
       float[] stockHighData = getStockData(100, 200, "high");
       float[] stockLowData = getStockData(100, 200, "low");

       double[] k = new double[100];
       double[] d = new double[100];

       Core core = new Core();

       RetCode ret = core.stoch(0, 99, stockHighData, stockLowData, stockCloseData, fastk, slowk, MAType.Sma, slowd, MAType.Sma, begin, end, k, d);

       for (int i = begin.value; i < stockLowData.length; i++) {
           StringBuilder line = new StringBuilder();
           line.append("Period #");
           line.append(i+1);
           line.append(" close= ");
           line.append(stockCloseData[i]);
           line.append(" %k=");
           line.append(k[i-begin.value]);
           line.append(" %d=");
           line.append(d[i-begin.value]);
           System.out.println(line.toString());
       }

   }




    public float returnIndication() {
        return 0;
    }
}

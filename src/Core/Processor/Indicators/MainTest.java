package Core.Processor.Indicators;

/**
 * Created by ZachBluhm on 2/22/15.
 */
public class MainTest {

    public static void main (String[]args) {

        MACD macd = new MACD("'AA'");

        float[] ema26 = macd.calculateTwentySixDayEMA();

        for (int i = 0; i < ema26.length; i++) {
            System.out.println(ema26[i]);
        }

    }

}

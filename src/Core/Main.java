package Core;

import Core.Processor.Indicators.AroonOsc;
import Core.Processor.Indicators.MACD;
import Core.Processor.Indicators.MAMA;
import Core.Processor.Indicators.RSI;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;

/**
 * Created by ZachBluhm on 2/20/15.
 */
public class Main {

    static ArrayList<String> good;
    static int afterMACD;

    public static void main(String[]args) {

        good = new ArrayList<String>();

        List<String> lines;

        try {
            lines = FileUtils.readLines(new File("/Users/ZachBluhm/stock-tracker/Data/stock_tickers1.txt"));
            int done = 0;
            for(String line : lines) {
                handleMACD(line);
                done++;
                if (done == 30) {
                    break;
                }
            }


            afterMACD = good.size();
            for (String stock : good) {
                ArrayList<String> goodCopy = good;
                handleRSI(stock, goodCopy);
                good = goodCopy;
            }
            for(String stock : good) {
                ArrayList<String> goodCopy = new ArrayList<String>();
                Collections.copy(goodCopy, good);
                handleMAMA(stock, goodCopy);
                Collections.copy(good, goodCopy);
            }
            for(Iterator<String> it = good.iterator(); it.hasNext();) {
                String stock = it.next();
                handleAROON(stock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(String stock : good) {
            System.out.println(stock);
        }

        System.out.println("After MACD: " + afterMACD);
        System.out.println("After RSI + MAMA + AROON: " + good.size());

    }

    public static void handleMACD(String stock) {

        MACD macd = new MACD(stock);

        macd.getMACD();

        double[] signal = macd.getSignal();
        double[] macdLine = macd.getMacd();

        if(signal == null || macdLine == null) {
            return;
        }

        for (int i = 145; i < signal.length; i++) {
            //System.out.println(macdLine[i - 33] + " vs " + signal[i - 33]);

            if(macdLine[i - 33] > signal[i - 33]) {

                if(i != 33 && macdLine[i-1-33] < signal[i-1-33]) {
                    good.add(stock);
                }
            }
        }
    }

    public static void handleRSI(String stock, ArrayList goodCopy) {
        RSI rsi = new RSI(stock);

        rsi.getRSI();

        double[] outRSI = rsi.getOut();

        if (outRSI == null) {
            System.out.println("null");
            return;
        }

        for(int i = 145; i < outRSI.length; i++) {
            if (outRSI[i-14] < 40 || outRSI[i-14] > 60) {
                goodCopy.remove(stock);
            }
        }
    }

    public static void handleMAMA(String stock, ArrayList goodCopy) {
        MAMA mama = new MAMA(stock);

        mama.getMAMA();

        double[] mamaLine = mama.getMama();
        double[] famaLine = mama.getFama();

        for(int i = 95; i < mamaLine.length; i++) {
            if(mamaLine[i - 33] < famaLine[i - 33]) {
                goodCopy.remove(stock);
            }
        }

    }

    public static void handleAROON(String stock) {
        AroonOsc aroon = new AroonOsc(stock);

        aroon.getAroonOsc();

        double[] aroonOsc = aroon.getOut();

        for(int i = 95; i < aroonOsc.length; i++) {
            if (aroonOsc[i-35] < 50) {
                good.remove(stock);
                System.out.println(stock + " removed");
            }
        }
    }

    public static void handleBeta() {

    }

    public static void handleBollingerBands() {

    }


}

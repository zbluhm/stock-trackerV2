package Core;

import Core.Processor.Indicators.MACD;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZachBluhm on 2/20/15.
 */
public class Main {

    static ArrayList<String> good;

    public static void main(String[]args) {

        good = new ArrayList<String>();

        List<String> lines;

        try {
            lines = FileUtils.readLines(new File("/Users/ZachBluhm/stock-tracker/Data/stock_tickers1.txt"));
            int done = 0;
            for(String line : lines) {
                handleMACD(line);
                done++;
                System.out.println(done + " done out of " + lines.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(String stock : good) {
            System.out.println(stock);
        }

        System.out.println(good.size());

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

    public static void handleBeta() {

    }

    public static void handleBollingerBands() {

    }


}

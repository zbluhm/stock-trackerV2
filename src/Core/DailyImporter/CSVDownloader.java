package Core.DailyImporter;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by ZachBluhm on 2/20/15.
 */
public class CSVDownloader {

    public CSVDownloader() {

    }

    public static void run() {


        String baseUrl = "http://ichart.finance.yahoo.com/table.csv?s=";
        String ID;
        String fDate = getFDate();
        String tDate = getFDate(); //"&d=1&e=15&f=2015";
        String interval = "&g=d";
        final String end = "&ignore=.csv";
        List<String> lines;

        try {
            lines = FileUtils.readLines(new File("/Users/ZachBluhm/stock-tracker/Data/stock_tickers1.txt"));

            int done = 0;

            for (String line : lines) {
                ID = line;
                try {
                    URL url = new URL(baseUrl + ID + tDate + fDate + interval + end);

                    File file = new File("/Users/ZachBluhm/stock-tracker/Data/DailyCSVs" + ID + ".csv");

                    FileUtils.copyURLToFile(url, file);
                    done++;
                    System.out.println(done + " downloaded of " + lines.size());

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(baseUrl + ID + fDate + tDate + interval + end);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getFDate() {
        DateTime date = new DateTime();
        int a = date.getMonthOfYear() - 1;
        int b = date.getDayOfMonth();

        String fDate = "&a=" + a + "&b=" + b + "&c=2015";

        return fDate;
    }

}

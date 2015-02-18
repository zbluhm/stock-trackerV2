package csvDataDownloader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

/**
 * Created by ZachBluhm on 2/18/15.
 */
public class Main {

    public static void main(String[]args) {

        String baseUrl = "http://ichart.finance.yahoo.com/table.csv?s=";
        String ID = "GOOG";
        String fDate = "&a=1&b=27&c=2009";
        String tDate = "&d=0&e=15&f=2015";
        String interval = "&g=d";
        final String end = "&ignore=.csv";

        try {

            URL url = new URL(baseUrl + ID + fDate + tDate + interval + end);
            //URL url = new URL("http://finance.yahoo.com/d/quotes.csv?s=AAPL+GOOG+MSFT&f=nab");

            File file = new File("/Users/ZachBluhm/Desktop/stock-tracker/Data/" + ID + ".csv");

            FileUtils.copyURLToFile(url, file);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(baseUrl+ID+fDate+tDate+interval+end);
        }
        //http://ichart.finance.yahoo.com/table.csv?s=GOOG&a=0&b=1&c=2013&d=3&e=1&f=2014&g=d&ignore=.csv
        //http://ichart.finance.yahoo.com/table.csv?s=APPL&a=1&b=6&c=2009&d=0&e=15&f=2015&g=d&ignore=.csv

    }

}
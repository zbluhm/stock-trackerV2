package csvDataDownloader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ZachBluhm on 2/18/15.
 */
public class Main {

    public static void main(String[]args) {

        String baseUrl = "http://ichart.finance.yahoo.com/table.csv?s=";
        String ID = "AAPL";
        String fDate = "&a=1&b=27&c=2009";
        String tDate = "&d=1&e=15&f=2015";
        String interval = "&g=d";
        final String end = "&ignore=.csv";
        List<String> lines;

        try {
            lines = FileUtils.readLines(new File("/Users/ZachBluhm/stock-tracker/Data/testloop.txt"));

            for (String line : lines) {
                ID = line;
                try {
                    URL url = new URL(baseUrl + ID + tDate + fDate + interval + end);

                    File file = new File("/Users/ZachBluhm/stock-tracker/Data/CSVs/" + ID + ".csv");

                    FileUtils.copyURLToFile(url, file);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(baseUrl+ID+fDate+tDate+interval+end);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
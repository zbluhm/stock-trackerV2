package DuplicateDeleter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZachBluhm on 2/20/15.
 */
public class Main {

    public static void main(String[]args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/ZachBluhm/stock-tracker/Data/stock_tickers.txt"));

            Set<String> lines = new HashSet<String>(10000);
            String line;

            while((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/ZachBluhm/stock-tracker/Data/stock_tickers1.txt"));

            for (String unique : lines) {
                writer.write(unique);
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

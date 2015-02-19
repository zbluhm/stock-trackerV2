package CSVParser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZachBluhm on 2/18/15.
 */
public class Main {

    private static final String[] FILE_HEADER_MAPPING = {"Date", "Open", "High", "Low", "Close", "Volume", "Adj Close"};
    private static final String DATE = "Date";
    private static final String CLOSE = "Close";

    public static void main(String[]args) {


        FileReader fileReader = null;
        CSVParser csvParser = null;
        List<Integer> highs = new ArrayList<Integer>();

        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

        try {

            fileReader = new FileReader("/Users/ZachBluhm/stock-tracker/Data/CSVs/AA.csv");

            csvParser = new CSVParser(fileReader, csvFormat);

            List csvRecords = csvParser.getRecords();

            for(int i = 1; i < csvRecords.size(); i++) {

                CSVRecord record = (CSVRecord) csvRecords.get(i);
                highs.add(Integer.parseInt(record.get(CLOSE)));

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (csvParser != null) {
                    csvParser.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        Integer sum = 0;
        for(Integer high : highs) {
            sum += high;
        }

        System.out.println(sum.doubleValue() / highs.size());

    }
}

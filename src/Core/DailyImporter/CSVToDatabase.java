package Core.DailyImporter;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

/**
 * Created by ZachBluhm on 2/20/15.
 */
public class CSVToDatabase {

    private static final String SQL_INSERT = "INSERT INTO ${table}(${keys}) VALUES(${values})";
    private static final String TABLE_REGEX = "\\$\\{table\\}";
    private static final String KEYS_REGEX = "\\$\\{keys\\}";
    private static final String VALUES_REGEX = "\\$\\{values\\}";

    private Connection connection;
    private char seperator;

    private String symbol;

    public CSVToDatabase(Connection connection, String symbol) {
        this.connection = connection;
        this.seperator = ',';
        this.symbol = symbol;

    }

    public void loadCSV(String csvFile, String tableName, boolean truncateBeforeLoad) throws Exception {

        CSVReader csvReader = null;
        if(null == this.connection) {
            throw new Exception("Not a valid connection");
        }
        try {
            csvReader = new CSVReader(new FileReader(csvFile), this.seperator);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while loading CSV file." + e.getMessage());
        }

        String[] headerRowCSV = csvReader.readNext();
        String[] headerRow = new String[8];
        headerRow[0] = "Symbol";
        for (int i = 0, x = 1; i < headerRowCSV.length; i ++, x++) {  // Expanding the array to make room for symbol
            headerRow[x] = headerRowCSV[i];
        }

        for (int i = 0; i < headerRow.length; i++) {                 //Setting the array content to lowercase
            if (i == headerRow.length - 1) {
                headerRow[i] = headerRow[i].replaceAll("\\s", "");   //Removes spaces from "adj close"
            }
            headerRow[i] = headerRow[i].toLowerCase();
        }

        if (null == headerRow)  {
            throw new FileNotFoundException("CSV file format might be fucked.");
        }

        String questionmarks = StringUtils.repeat("?,", headerRow.length);
        questionmarks = (String) questionmarks.subSequence(0, questionmarks.length() - 1);

        String query = SQL_INSERT.replaceFirst(TABLE_REGEX, tableName);
        query = query.replaceFirst(KEYS_REGEX, StringUtils.join(headerRow, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        System.out.println("Query: " + query);

        String[] nextLine;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = this.connection;
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);

            if (truncateBeforeLoad) {
                con.createStatement().execute("DELETE FROM " + tableName);
            }

            final int batchSize = 1000;
            int count = 0;
            Date date = null;

            while ((nextLine = csvReader.readNext()) != null) {

                if (null != nextLine) {
                    ps.setString(1, symbol);
                    int index = 2;
                    for(String string : nextLine) {
                        ps.setString(index++, string);
                    }
                    ps.addBatch();
                }
                if(++count % batchSize == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
            con.commit();
        }
        catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            throw new Exception("Error occured while sending file data to database." + e.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
                csvReader.close();
            }
        }
    }

    public char getSeperator() {
        return seperator;
    }

    public void setSeperator(char s) {
        this.seperator = s;
    }
}

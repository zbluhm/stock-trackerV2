package Core.Processor.Indicators;

/**
 * Created by ZachBluhm on 2/22/15.
 */
public class MainTest {

    public static void main (String[]args) {

        AroonOsc arron = new AroonOsc("AAPL");

        arron.getAroonOsc();
    }

}

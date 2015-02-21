package RenjinTest;

import org.renjin.sexp.ListVector;
import org.renjin.sexp.Vector;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Created by ZachBluhm on 2/21/15.
 */
public class RTest {


    public static void main(String[]args)  {
        ScriptEngineManager manager = new ScriptEngineManager();

        ScriptEngine engine = manager.getEngineByName("Renjin");

        if (engine == null) {
            System.out.println("Error,");
        }
        try {
            ListVector model = (ListVector)engine.eval("x <- 1:10; y <- x*3; lm(y ~ x)");
            Vector c = model.getElementAsVector("coefficients");

            System.out.println("intercept: " + c.getElementAsDouble(0));
            System.out.println("slope: " + c.getElementAsDouble(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

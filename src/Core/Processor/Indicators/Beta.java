package Core.Processor.Indicators;

/**
 * Created by barkerc1 on 2/20/15.
 */
public class Beta implements Indicator {

    private double stockReturn;
    private float beta;

    public Beta() {
    }

    // TODO: find indicators to returns
    public float returnIndication () {
        setBeta();
        return beta;
    }

    public double getStockReturn() {
        return 0;
    }

    public double getStockAvgReturn() {
        return 0;
    }

    public double getReturnMinusAvgReturn() {
        return 0;
    }

    public double getCovar() {
        double sum;

        return 0;
    }

    public double getVar() {
        double sum;

        return 0;
    }

    public void setBeta() {
        beta = (float) (getCovar() / getVar());
    }
}
//        Sw = stock return % from previous weekly
//        rw = market return % from previous weekly
//        n  = number of week
//
//        cov(Sw, rw) = Sum((Sw@n - SwBAR)(rw@n - rwBAR)) / (n - 1)
//        var(rw) = Sum((rw@n - rwBAR)^2) / (n - 1)
//        b = cov(Sw, rw) / var(rw)
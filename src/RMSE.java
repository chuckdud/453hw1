import java.io.IOException;
import java.io.PrintWriter;

//Root mean squared error
public class RMSE{

    PrintWriter writer;

    public RMSE(PrintWriter writer) {
        this.writer = writer;
    }

    public void test(double[] a, double[] b, double sensitivity, double epsilon, int arrNum) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("The arrays must have the same length.");
        }

        int n = a.length;
        double sum = 0;

        for (int i = 0; i < n; i++) {
            double diff = a[i] - b[i];
            sum += diff * diff;
        }

            switch(arrNum){
                case 0:
                    writer.printf("\"RMSE test on arrays (25Higher - LessOneOldest) using [ epsilon %s sensitivity %s ]: %s\n", epsilon, sensitivity, Math.sqrt(sum / n));
                    break;
                case 1:
                    writer.printf("\"RMSE test on arrays (25Higher - No26) using [ epsilon %s sensitivity %s ]: %s\n", epsilon, sensitivity, Math.sqrt(sum / n));
                    break;
                case 2:
                    writer.printf("\"RMSE test on arrays (25Higher - NoYoungest) using [ epsilon %s sensitivity %s ]: %s\n", epsilon, sensitivity, Math.sqrt(sum / n));
                    break;
                default:
                    writer.println("Can`t work with more than 3 arrays");

            }

            System.out.println("Ooutput written to file rmse_indistinguishability_test.txt!");

//        System.out.printf("\"RMSE test using [ epsilon %s sensitivity %s ]: %s\n", epsilon, sensitivity, Math.sqrt(sum / n));

//        return Math.sqrt(sum / n);
    }
    public void computeResult(double[] original, double sensitivity, double epsilon, double[]... arrays){
        int counter = 0;
        for(double[] arr : arrays){

            test(original, arr, sensitivity, epsilon, counter );
            counter++;
        }
    }


}


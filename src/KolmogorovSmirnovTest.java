import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;



//KolmogorovSmirnovTest

public class KolmogorovSmirnovTest {

    PrintWriter writer;

    public KolmogorovSmirnovTest(PrintWriter writer) {
        this.writer = writer;
    }

    public  void ksTest(double[] data1, double[] data2, double sensitivity, double epsilon, int arrNum) {
        int n1 = data1.length;
        int n2 = data2.length;
        Arrays.sort(data1);
        Arrays.sort(data2);
        int i = 0;
        int j = 0;
        double d = 0.0;
        while (i < n1 && j < n2) {
            double x1 = data1[i];
            double x2 = data2[j];
            if (x1 < x2) {
                i++;
                d = Math.max(d, (i - j - 1.0) / n1);
            } else if (x1 > x2) {
                j++;
                d = Math.max(d, (j - i - 1.0) / n2);
            } else {
                i++;
                j++;
            }
        }


        switch (arrNum) {
            case 0:
                writer.printf("Kolmogorov-Smirnov Test on arrays (25Higher - LessOneOldest) using [ epsilon %s sensitivity %s ]: %s\n", epsilon, sensitivity, d);

                break;
            case 1:
                writer.printf("Kolmogorov-Smirnov Test on arrays (25Higher - No26) using [ epsilon %s sensitivity %s ]: %s\n", epsilon, sensitivity, d);

                break;
            case 2:
                writer.printf("Kolmogorov-Smirnov Test on arrays (25Higher - NoYoungest) using [ epsilon %s sensitivity %s ]: %s\n", epsilon, sensitivity, d);

                break;
            default:
                writer.println("Can`t work with more than 3 arrays");

        }

        System.out.println("Output written to file kolmogorov-smirnov_indistinguishability_test.txt!");

}



    public  void computeResult(double[] original, double sensitivity, double epsilon, double[]... arrays) {

        int counter = 0;
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("./kolmogorov-smirnov_indistinguishability_test.txt", "UTF-8");
            for (double[] arr : arrays) {

                ksTest(original, arr, sensitivity, epsilon, counter);
                counter++;
            }
        } catch (IOException e) {
            System.out.println("Error writing output to file: " + e.getMessage());

        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }
}


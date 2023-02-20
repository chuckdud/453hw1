

    import java.io.IOException;
    import java.io.PrintWriter;
    import java.text.DecimalFormat;
    import java.util.Random;

    public class LaplaceNoiseGenerator {
        double  averageAge25Higher;
        double averageAgeLessOneOldest;
        double averageAgeNot26;
        double averageAgeNoYoungest;

        double GS; //global sensitivity

        PrintWriter writer;

        public LaplaceNoiseGenerator(double averageAge25Higher, double averageAgeLessOneOldest, double averageAgeNot26, double averageAgeNoYoungest, double GS, PrintWriter writer) {
            this.averageAge25Higher = averageAge25Higher;
            this.averageAgeLessOneOldest = averageAgeLessOneOldest;
            this.averageAgeNot26 = averageAgeNot26;
            this.averageAgeNoYoungest = averageAgeNoYoungest;
            this.GS = GS;
            this.writer = writer;

        }

        public static double generateNoise(double sensitivity, double epsilon) {
            Random random = new Random();
            double u = random.nextDouble() - 0.5;
            return -sensitivity * Math.signum(u) * Math.log(1 - 2 * Math.abs(u)) / epsilon;
        }
        public static double roundTwoDecimalPlaces(double toRound){
            DecimalFormat df = new DecimalFormat("#.##");
            return Double.parseDouble(df.format(toRound));
        }
        public void computeLaplaceNoise(double[] arr_averageAge25Higher, double[] arr_averageAgeLessOneOldest, double[] arr_averageAgeNoYoungest,double[] arr_averageAgeNot26, double epsilon){

                writer.printf("\n-------------------------------------------------------\n");
                writer.printf("Noisy output of dataset with age greater than 25 using. s1 = %s  e = %s: \n", 0.001579, epsilon );
                writer.printf("-------------------------------------------------------\n");
                for (int i = 0; i < 1000; i++) {
                    arr_averageAge25Higher[i] = roundTwoDecimalPlaces(averageAge25Higher + LaplaceNoiseGenerator.generateNoise(0.001579, 0.5));
                    writer.printf("%s, ",arr_averageAge25Higher[i]);

                }
                writer.printf("\n-------------------------------------------------------\n");
                writer.printf("Noisy output of dataset without one oldest age record. s2 = %s  e = %s: \n", 1.3, epsilon );
                writer.printf("-------------------------------------------------------\n");
                for (int i = 0; i < 1000; i++) {
                    arr_averageAgeLessOneOldest[i] = roundTwoDecimalPlaces(averageAgeLessOneOldest + LaplaceNoiseGenerator.generateNoise(0.001579, 0.5));
                    writer.printf("%s, ",arr_averageAgeLessOneOldest[i]);

                }
                writer.printf("\n-------------------------------------------------------\n");
                writer.printf("Noisy output of dataset without without any age 26. s3 = %s  e = %s: \n", GS, epsilon);
                writer.printf("-------------------------------------------------------\n");
                for (int i = 0; i < 1000; i++) {
                    arr_averageAgeNot26[i] = roundTwoDecimalPlaces(averageAgeNot26 + LaplaceNoiseGenerator.generateNoise(GS, 0.5));
                    writer.printf("%s, ",arr_averageAgeNot26[i]);

                }
                writer.printf("\n-------------------------------------------------------\n");
                writer.printf("Noisy output of dataset without any youngest age. s4 = %s  e = %s: \n", GS, epsilon);
                writer.printf("-------------------------------------------------------\n");
                for (int i = 0; i < 1000; i++) {
                    arr_averageAgeNoYoungest[i] = roundTwoDecimalPlaces(averageAgeNoYoungest + LaplaceNoiseGenerator.generateNoise(GS, 0.5));
                    writer.printf("%s, ",arr_averageAgeNoYoungest[i]);

                }
        }

    }



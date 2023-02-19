

    import java.util.Random;

    public class LaplaceNoiseGenerator {
        public static double generateNoise(double sensitivity, double epsilon) {
            Random random = new Random();
            double u = random.nextDouble() - 0.5;
            return -sensitivity * Math.signum(u) * Math.log(1 - 2 * Math.abs(u)) / epsilon;
        }
    }



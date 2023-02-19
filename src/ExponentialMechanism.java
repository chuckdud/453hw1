import java.util.List;
import java.util.Random;

public class ExponentialMechanism {
    private static final double EULER_MASCHERONI = 0.57721566490153286;
    private final double epsilon;
    private final double sensitivity;

    public ExponentialMechanism(double epsilon, double sensitivity) {
        this.epsilon = epsilon;
        this.sensitivity = sensitivity;
    }

    public <T> T run(List<T> candidates, ScoringFunction<T> scoringFunction) {
        double[] probabilities = new double[candidates.size()];
        double maxScore = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < candidates.size(); i++) {
            T candidate = candidates.get(i);
            double score = scoringFunction.score(candidate);
            maxScore = Math.max(maxScore, score);
            probabilities[i] = Math.exp(epsilon * score / (2 * sensitivity));
        }

        double totalProbability = 0.0;
        for (double probability : probabilities) {
            totalProbability += probability;
        }

        for (int i = 0; i < candidates.size(); i++) {
            probabilities[i] /= totalProbability;
        }

        double randomValue = new Random().nextDouble();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < candidates.size(); i++) {
            cumulativeProbability += probabilities[i];
            if (cumulativeProbability >= randomValue) {
                return candidates.get(i);
            }
        }

        return candidates.get(candidates.size() - 1);
    }

    public interface ScoringFunction<T> {
        double score(T candidate);
    }
}


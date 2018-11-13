import java.text.DecimalFormat;
import java.util.Comparator;

public class Suggestion implements Comparable {
    private String distributionKey;
    private String sortKey;
    private double confidence;

    public Suggestion(String distributionKey, String sortKey, double confidence) {
        this.distributionKey = distributionKey;
        this.sortKey = sortKey;

        this.confidence = confidence;
    }

    public double getConfidence() {
        return confidence;
    }

    @Override
    public String toString() {
        DecimalFormat format = new DecimalFormat("#0.00");
        return distributionKey + ",\"" + sortKey + "\"," + format.format(confidence * 100) + "%,";
    }

    @Override
    public int compareTo(Object o) {
        double conf1 = ((Suggestion) o).getConfidence();
        double conf2 = this.confidence;
        if (conf1 > conf2) {
            return 1;
        } else if (conf1 < conf2) {
            return -1;
        } else {
            return 0;
        }
    }
}

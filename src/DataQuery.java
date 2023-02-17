public class DataQuery {

    private double average;
    private double totalCount;
    private double sum;

    private double sensitivity;

    public DataQuery(double average, double totalCount, double sum, double sensitivity) {
        this.average = average;
        this.totalCount = totalCount;
        this.sum = sum;
        this.sensitivity = sensitivity;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(double totalCount) {
        this.totalCount = totalCount;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }
}

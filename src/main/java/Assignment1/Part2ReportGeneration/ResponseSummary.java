package Assignment1.Part2ReportGeneration;

public class ResponseSummary {

    long mean_response_time;
    long median_response_time;
    long p99;
    long min_response_time;
    long max_response_time;

    public ResponseSummary(long mean_response_time, long median_response_time, long p99, long min_response_time, long max_response_time) {
        this.mean_response_time = mean_response_time;
        this.median_response_time = median_response_time;
        this.p99 = p99;
        this.min_response_time = min_response_time;
        this.max_response_time = max_response_time;
    }

    public long getMean_response_time() {
        return mean_response_time;
    }

    public void setMean_response_time(long mean_response_time) {
        this.mean_response_time = mean_response_time;
    }

    public long getMedian_response_time() {
        return median_response_time;
    }

    public void setMedian_response_time(long median_response_time) {
        this.median_response_time = median_response_time;
    }

    public long getP99() {
        return p99;
    }

    public void setP99(long p99) {
        this.p99 = p99;
    }

    public long getMin_response_time() {
        return min_response_time;
    }

    public void setMin_response_time(long min_response_time) {
        this.min_response_time = min_response_time;
    }

    public long getMax_response_time() {
        return max_response_time;
    }

    public void setMax_response_time(long max_response_time) {
        this.max_response_time = max_response_time;
    }
}

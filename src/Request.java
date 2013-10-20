import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 20/10/13
 *        Made with Love
 */
public class Request implements Comparable {

    private int from;
    private int to;
    private double start;
    private double runtime;
    private List<Integer> path;

    public Request(int from, int to, double start, double runtime) {
        this.from = from;
        this.to = to;
        this.start = start;
        this.runtime = runtime;
        this.path = null;
    }

    public double getStartTime() {
        return start;
    }

    public double getRunTime() {
        return runtime;
    }

    public double getFinishTime() {
        return (this.getStartTime() + this.getRunTime());
    }

    public void setPath(List<Integer> path) {
        this.path = path;
    }

    public List<Integer> getPath() {
        return this.path;
    }

    @Override
    public int compareTo(Object o) {
        Request other = (Request) o;
        int comparison = 0;

        if (this.getFinishTime() < other.getFinishTime()) {
            comparison = -1;
        } else if (this.getFinishTime() > other.getFinishTime()) {
            comparison = 1;
        }
        return comparison;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}

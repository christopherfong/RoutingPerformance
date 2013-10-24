/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 * Made with Love
 */
public class ShortestDelay implements Cost {

    private double accumulatedDelay;

    public ShortestDelay() {
        this.accumulatedDelay = Double.MAX_VALUE;
    }

    public ShortestDelay(Edge e) {
        this.accumulatedDelay = e.getPropagationDelay();
    }

    public double getAccumulatedDelay() {
        return accumulatedDelay;
    }

    @Override
    public Cost clone() {
        ShortestDelay clone = new ShortestDelay();
        clone.accumulatedDelay = this.getAccumulatedDelay();
        return clone;
    }

    @Override
    public double get() {
        return this.accumulatedDelay;
    }

    @Override
    public void setStart() {
        this.accumulatedDelay = 0;
    }

    @Override
    public void updateCost(Edge e) {
        this.accumulatedDelay += e.getPropagationDelay();
    }

    @Override
    public double calculateNewCost(Edge e) {
        return (this.accumulatedDelay + e.getPropagationDelay());
    }

}

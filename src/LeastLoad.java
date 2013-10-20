/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 *        Made with Love
 */
public class LeastLoad implements Cost {

    private int maxCapacity;
    private int currentLoad;

    public LeastLoad() {
        this.maxCapacity = 0;
        this.currentLoad = 0;
    }

    public LeastLoad(Edge e) {
        this.maxCapacity = e.getCircuitCapacity();
        this.currentLoad = e.getCurrentLoad();
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public Cost clone() {
        LeastLoad clone = new LeastLoad();
        clone.maxCapacity = this.getMaxCapacity();
        clone.currentLoad = this.getCurrentLoad();

        return clone;
    }

    @Override
    public double getCost() {
        double cost = 1;
        if (maxCapacity != 0) {
            cost = (double) currentLoad / maxCapacity;
        }
        return cost;
    }

    @Override
    public void setStart() {
        this.maxCapacity = 0;
        this.currentLoad = 0;
    }

    @Override
    public void updateCost(Edge e) {
        this.currentLoad += e.getCurrentLoad();
        this.maxCapacity += e.getCircuitCapacity();
    }

    @Override
    public double calculateNewCost(Edge e) {
        return (double) (currentLoad + e.getCurrentLoad()) / (double) (maxCapacity + e.getCircuitCapacity());
    }
}

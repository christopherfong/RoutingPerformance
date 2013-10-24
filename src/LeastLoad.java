import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 * Made with Love
 */
public class LeastLoad implements Cost {

    private PriorityQueue<Double> loads;

    public LeastLoad() {
        this.loads = new PriorityQueue<Double>(10, Collections.reverseOrder());
    }

    public LeastLoad(Edge e) {
        this.loads = new PriorityQueue<Double>(10, Collections.reverseOrder());
        this.updateCost(e);
    }

    @Override
    public Cost clone() {
        LeastLoad clone = new LeastLoad();
        for (double d : loads) {
            clone.loads.add(d);
        }
        return clone;
    }

    @Override
    public double get() {
        return loads.peek();
    }

    @Override
    public void setStart() {
        this.loads.clear();
    }

    @Override
    public void updateCost(Edge e) {
        double load = getRatio(e);
        loads.add(load);
    }

    @Override
    public double calculateNewCost(Edge e) {
        double newCost = getRatio(e);
        double oldCost = loads.peek();
        if (oldCost < newCost) {
            newCost = oldCost;
        }
        return newCost;
    }

    private double getRatio(Edge e) {
        double currentLoad = e.getCurrentLoad();
        double circuitCapacity = e.getCircuitCapacity();
        return (currentLoad / circuitCapacity);
    }

}

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 *        Made with Love
 */
public class Edge  {

    private static final int PROPAGATION_DELAY_MAX = 200;
    private static final int VIRTUAL_CIRCUITS_MAX = 100;

    private int circuitCapacity;
    private int propagationDelay;

    private int currentVirtualCircuits;

    public Edge(int propagationDelay, int circuitCapacity) throws IllegalArgumentException {

        if (propagationDelay > PROPAGATION_DELAY_MAX) {
            throw new IllegalArgumentException("[Edge] propagationDelay greater than 200");
        }

        if (circuitCapacity > VIRTUAL_CIRCUITS_MAX) {
            throw new IllegalArgumentException("[Edge] circuitCapacity greater than 100");
        }

        this.circuitCapacity = circuitCapacity;
        this.currentVirtualCircuits = 0;
        this.propagationDelay = propagationDelay;

    }

    public int getCircuitCapacity() {
        return this.circuitCapacity;
    }

    public int getPropagationDelay() {
        return this.propagationDelay;
    }

    public int getCurrentLoad() {
        return this.currentVirtualCircuits;
    }

    public void setLoad(int newCount) {
        this.currentVirtualCircuits = newCount;
    }

    public void raiseLoad() {
        currentVirtualCircuits++;
    }

    public void lowerLoad() throws ArithmeticException {
        if (currentVirtualCircuits > 0) {
            currentVirtualCircuits--;
        } else {
            throw new ArithmeticException("[Edge] lowerLoad: Link is currently empty.\n");
        }
    }

}

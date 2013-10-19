/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 *        Made with Love
 */
public class Node {

    private static final int PROPAGATION_DELAY_MAX = 100;
    private static final int VIRTUAL_CIRCUITS_MAX = 100;

    private int circuitCapacity;
    private int propagationDelay;

    private int currentVirtualCircuits;

    public Node (int propagationDelay, int circuitCapacity) throws IllegalArgumentException {

        if (circuitCapacity > VIRTUAL_CIRCUITS_MAX) {
            throw new IllegalArgumentException("[Node] circuitCapacity greater than 100");
        }

        if (propagationDelay > PROPAGATION_DELAY_MAX) {
            throw new IllegalArgumentException("[Node] propagationDelay greater than 100");
        }

        this.circuitCapacity = circuitCapacity;
        this.currentVirtualCircuits = 0;
        this.propagationDelay = propagationDelay;

    }

    public int getCircuitCapacity() {
        return this.circuitCapacity;
    }

    public int getPropagationDelay () {
        return this.propagationDelay;
    }

    public int getCurrentVirtualCircuits () {
        return this.currentVirtualCircuits;
    }

    public void addVirtualCircuits () {
        currentVirtualCircuits++;
    }

    public void removeVirtualCircuit () throws ArithmeticException {
        if (currentVirtualCircuits > 0) {
            currentVirtualCircuits--;
        } else {
            throw new ArithmeticException("removeVirtualCircuit: Link is currently empty.\n");
        }
    }

}

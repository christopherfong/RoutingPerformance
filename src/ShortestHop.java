/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 * Made with Love
 */
public class ShortestHop implements Cost {

    private int hops;

    public ShortestHop() {
        this.hops = Integer.MAX_VALUE;
    }

    public ShortestHop(Edge e) {
        this.hops = 1;
    }

    public int getHops() {
        return this.hops;
    }

    @Override
    public Cost clone() {
        ShortestHop clone = new ShortestHop();
        clone.hops = this.getHops();
        return clone;
    }

    @Override
    public double get() {
        return (double) this.hops;
    }

    @Override
    public void setStart() {
        this.hops = 0;
    }

    @Override
    public void updateCost(Edge e) {
        this.hops += 1;
    }

    @Override
    public double calculateNewCost(Edge e) {
        return (double) this.hops + 1;
    }

}
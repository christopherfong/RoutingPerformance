/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 * Made with Love
 */
public class Vertex implements Comparable {

    private int index;
    private Cost cost;

    public Vertex(int index, Cost cost) {
        this.index = index;
        this.cost = cost;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getCost() {
        return cost.get();
    }

    public void updateCost(Cost c, Edge e) {
        this.cost = c.clone();
        this.cost.updateCost(e);
    }

    public Cost cloneCost () {
        return cost.clone();
    }

    public void print() {
        System.out.println("Vertex " + index+'A');
        System.out.println(" Cost "+cost.get());
    }

    @Override
    public int compareTo(Object o) throws NullPointerException {
        Vertex toCompare = (Vertex) o;
        int comparison = 0;
        if (this.cost.get() < toCompare.cost.get()) {
            comparison = -1;
        } else if (this.cost.get() > toCompare.cost.get()) {
            comparison = 1;
        }
        return comparison;
    }

}

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 *        Made with Love
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

    public Cost getCost() {
        return cost;
    }

    public void updateCost(Cost c, Edge e) {
        this.cost = c.clone();
        this.cost.updateCost(e);
    }

    @Override
    public int compareTo(Object o) throws NullPointerException {
        Vertex toCompare = (Vertex) o;
        int comparison = 0;
        if (this.getCost().getCost() < toCompare.getCost().getCost()) {
            comparison = -1;
        } else if (this.getCost().getCost() > toCompare.getCost().getCost()) {
            comparison = 1;
        }
        return comparison;
    }

}

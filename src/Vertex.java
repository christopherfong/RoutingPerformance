/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 *        Made with Love
 */
public class Vertex implements Comparable {

    private int index;
    private int cost;

    public Vertex (int index, int cost) {
        this.index = index;
        this.cost = cost;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(Object o) throws NullPointerException {
        Vertex toCompare = (Vertex)o;
        return (this.getCost() - toCompare.getCost());
    }

}

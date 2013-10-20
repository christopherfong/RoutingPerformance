/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 20/10/13
 *        Made with Love
 */
public interface Cost {

    public Cost clone();

    public double getCost();

    public void setStart();

    public void updateCost(Edge e);

    public double calculateNewCost(Edge e);

}

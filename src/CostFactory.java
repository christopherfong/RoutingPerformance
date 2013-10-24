/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 20/10/13
 * Made with Love
 */
public class CostFactory {

    private Algorithm algorithm;

    public CostFactory(Algorithm a) {
        this.algorithm = a;
    }

    public Algorithm getAlgorithm() {
        return this.algorithm;
    }

    public Cost newCost() {
        if (algorithm == Algorithm.SHORTEST_HOP) {
            return new ShortestHop();
        } else if (algorithm == Algorithm.SHORTEST_DELAY) {
            return new ShortestDelay();
        } else if (algorithm == Algorithm.LEAST_LOAD) {
            return new LeastLoad();
        }

        return null;
    }

    public Cost initialCost(Edge e) {
        if (algorithm == Algorithm.SHORTEST_HOP) {
            return new ShortestHop(e);
        } else if (algorithm == Algorithm.SHORTEST_DELAY) {
            return new ShortestDelay(e);
        } else if (algorithm == Algorithm.LEAST_LOAD) {
            return new LeastLoad(e);
        }
        return null;
    }

}

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 20/10/13
 *        Made with Love
 */
public class Djikstra implements PathFinder {
    private static final int EMPTY = -20;

    private Network network;
    private CostFactory factory;

    public Djikstra(Network network, Algorithm algorithm) {
        this.network = network;
        this.factory = new CostFactory(algorithm);
    }

    @Override
    public List<Integer> find(int from, int to) {

        boolean success = false;

        Cost[] distances = new Cost[Network.NODES_MAX];
        int[] previous = new int[Network.NODES_MAX];

        for (int i = 0; i < distances.length; i++) {
            distances[i] = null;
            previous[i] = EMPTY;
        }

        distances[from] = factory.newCost();
        distances[from].setStart();
        previous[from] = from;

        PriorityQueue<Vertex> candidates = new PriorityQueue<Vertex>();

        for (int i = 0; i < Network.NODES_MAX; i++) {
            if (i != from && network.isAdjacent(from, i)) {
                Edge e = network.getEdge(from, i);
                previous[i] = from;
                candidates.add(new Vertex(i, factory.initialCost(e)));
            }
        }

        while (!candidates.isEmpty()) {

            Vertex v = candidates.poll();
            int currentIndex = v.getIndex();
            distances[currentIndex] = v.getCost().clone();

            if (v.getIndex() == to) {
                success = true;
                break;
            }

            for (int i = 0; i < Network.NODES_MAX; i++) {
                if (distances[i] == null && network.isAdjacent(i, currentIndex)) {
                    v = findVertex(candidates, i);
                    Edge e = network.getEdge(i, currentIndex);
                    if (v != null && v.getCost().getCost() > distances[currentIndex].calculateNewCost(e)) {
                        v.updateCost(distances[currentIndex], e);
                        previous[i] = currentIndex;
                    } else if (v == null) {
                        Cost clone = distances[currentIndex].clone();
                        clone.updateCost(e);
                        candidates.add(new Vertex(i, clone));
                        previous[i] = currentIndex;
                    }
                }
            }

        }

        if (RoutingPerformance.DEBUG) {
            //printArray(distances, false);
            printArray(previous, true);
        }

        ArrayList<Integer> path = null;
        if (success) {
            path = new ArrayList<Integer>();

            int index = to;
            while (previous[index] != index) {
                path.add(index);
                index = previous[index];
            }
            path.add(index);
        }

        return path;

    }

    private Vertex findVertex(PriorityQueue<Vertex> candidates, int index) {
        Vertex v = null;
        for (Vertex iterator : candidates) {
            if (iterator.getIndex() == index) {
                v = iterator;
                break;
            }
        }
        return v;
    }

    private void printArray(int[] array, boolean characters) {
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%c ", (char) (i + 'A'));
        }
        System.out.println();

        for (int i : array) {
            if (characters) {
                System.out.printf("%c ", (char) (i + 'A'));
            } else if (array[i] == Integer.MAX_VALUE) {
                System.out.printf("- ");
            } else {
                System.out.printf("%d ", i);
            }
        }
        System.out.println();
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 *        Made with Love
 */
public class ShortestHop implements PathFinder{

    private static final int EMPTY = -20;

    private Network network;

    public ShortestHop (Network network) {
        this.network = network;
    }

    @Override
    public List<Integer> find(int from, int to) {

        boolean success = false;

        int[] distances = new int[Network.NODES_MAX];
        int[] previous = new int[Network.NODES_MAX];

        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE;
            previous[i] = EMPTY;
        }

        distances[from] = 0;
        previous[from] = from;

        PriorityQueue<Vertex> candidates = new PriorityQueue<Vertex>();

        for (int i = 0; i < Network.NODES_MAX; i++) {
            Vertex v;
            if (i != from && network.isAdjacent(from, i)) {
                 v = new Vertex(i, 1);
                previous[i] = from;
                candidates.add(v);
            }
        }

        while (!candidates.isEmpty()) {

            Vertex v = candidates.poll();
            int currentIndex = v.getIndex();
            distances[currentIndex] = v.getCost();
            if (v.getIndex() == to) {
                success = true;
                break;
            }

            for (int i = 0; i < Network.NODES_MAX; i++) {
                if (distances[i] == Integer.MAX_VALUE && network.isAdjacent(i, currentIndex)){
                    v = findVertex(candidates, i);
                    if (v != null && v.getCost() > distances[currentIndex] + 1) {
                        v.setCost(distances[currentIndex]+1);
                        previous[i] = currentIndex;
                    } else if (v == null) {
                        candidates.add(new Vertex(i,distances[currentIndex] + 1));
                        previous[i] = currentIndex;
                    }
                }
            }

        }

        if (RoutingPerformance.DEBUG) {
            printArray(distances, false);
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

    private Vertex findVertex (PriorityQueue<Vertex> candidates, int index) {
        Vertex v = null;
        for (Vertex iterator : candidates) {
            if (iterator.getIndex() == index) {
                v = iterator;
                break;
            }
        }
        return v;
    }

    private void printArray (int []array, boolean characters) {
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%c ", (char)(i+'A'));
        }
        System.out.println();

        for (int i : array) {
            if (characters) {
                System.out.printf("%c ", (char)(i+'A'));
            } else if (array[i] == Integer.MAX_VALUE) {
                System.out.printf("- ");
            } else {
                System.out.printf("%d ", i);
            }
        }
        System.out.println();
    }

    /*
    private void printLoad (int from, int to) {
        Edge e = network.getEdge(from, to);
        System.out.printf("%c<->%c: %d/%d\n",(char)(from+'A'),(char)(to+'A'),
                                                 e.getCurrentLoad(),e.getCircuitCapacity());
    }
    */
}
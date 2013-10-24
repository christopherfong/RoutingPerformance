import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 20/10/13
 * Made with Love
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

        int[] adjacentLocations;// = network.connectedLocations(from);
        for (int neighbour = 0; neighbour < Network.NODES_MAX; neighbour++) {
            Cost c;
            if (neighbour != from) {
                if (network.isAdjacent(from, neighbour)) {
                    Edge e = network.getEdge(from, neighbour);
                    /*
                    System.out.printf("Neighbour #%c\n",neighbour+'A');
                    System.out.println(" currentLoad: "+e.getCurrentLoad());
                    System.out.println(" circuitCapacity: "+e.getCircuitCapacity());
                    */
                    c = factory.initialCost(e);
                    previous[neighbour] = from;
                } else {
                    c = factory.newCost();
                }
                candidates.add(new Vertex(neighbour, c));
            }

        }

        boolean broken = false;

        while (!candidates.isEmpty()) {

            Vertex v = candidates.poll();
            if (v.getCost() == factory.newCost().get()) {
                if (RoutingPerformance.DEBUG) {
                    v.print();
                    System.out.println("Break!\n");
                }
                broken = true;
                break;
            }

            int currentIndex = v.getIndex();
            distances[currentIndex] = v.cloneCost();

            if (v.getIndex() == to) {
                success = true;
                break;
            }

            adjacentLocations = network.connectedLocations(currentIndex);
            for (int neighbour : adjacentLocations) {
                if (distances[neighbour] == null) {
                    v = findVertex(candidates, neighbour);
                    Edge e = network.getEdge(neighbour, currentIndex);
                    if (v.getCost() > distances[currentIndex].calculateNewCost(e)) {
                        candidates.remove(v);
                        v.updateCost(distances[currentIndex], e);
                        previous[neighbour] = currentIndex;
                        candidates.add(v);

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

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

    private Network network;

    public ShortestHop (Network network) {
        this.network = network;
    }

    @Override
    public List<Integer> find(int from, int to) {

        boolean failed = false;

        int[] distances = new int[Network.NODES_MAX];
        int[] previous = new int[Network.NODES_MAX];
        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE;
            previous[i] = -20;
        }

        distances[from] = 0;
        previous[from] = from;

        PriorityQueue<Vertex> candidates = new PriorityQueue<Vertex>();

        for (int i = 0; i < Network.NODES_MAX; i++) {
            if (i != from && network.isValidRoute(from, i)) {
                Vertex v = new Vertex(i,1);
                previous[i] = from;
                candidates.add(v);
            }
        }

        while (!candidates.isEmpty()) {

            Vertex v = candidates.poll();
            int currentIndex = v.getIndex();
            //System.out.printf("Examining index: %c\n",(char)(currentIndex + 'A'));
            distances[currentIndex] = v.getCost();
            if (v.getIndex() == to) {
                break;
            }

            for (int i = 0; i < Network.NODES_MAX; i++) {
                if (i != currentIndex && distances[i] == Integer.MAX_VALUE) {
                    if (!network.isValidRoute(i, currentIndex)) {
                        failed = true;
                        break;
                    } else {
                        v = null;
                        for (Vertex iterator : candidates) {
                            if (iterator.getIndex() == i) {
                                v = iterator;
                                break;
                            }
                        }

                        if (v != null && (v.getCost() > distances[currentIndex] + 1)) {
                            v.setCost(distances[currentIndex]+1);
                            previous[i] = currentIndex;
                        } else if (v == null) {
                            candidates.add(new Vertex(i,distances[currentIndex] + 1));
                            previous[i] = currentIndex;
                        }

                    }
                }
            }
        }

        /*
        System.out.print("[ShortestHop] distances: ");
        for (int i : distances) {
            if (i != Integer.MAX_VALUE) {
                System.out.print(i + " ");
            } else {
                System.out.print("- ");
            }
        }
        System.out.println();

        for (int i = 0; i < 26; i++) {
            System.out.printf("%c ", (char)(i + 'A'));
        }
        System.out.println();
        for (int i : previous) {
            System.out.printf("%c ",(char)(i + 'A'));
        }
        System.out.println();
        */

        ArrayList<Integer> path = null;
        if (!failed) {
            path = new ArrayList<Integer>();

            int index = to;
            while (index != -20 && previous[index] != index) {
                path.add(index);
                index = previous[index];
            }
            path.add(index);
        }

        return path;

    }

}

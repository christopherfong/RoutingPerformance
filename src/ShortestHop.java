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
    public List<Integer> find(char from, char to) {

        int fromIndex = network.translate(from);
        int toIndex = network.translate(to);

        int[] distances = new int[Network.NODES_MAX];
        int[] previous = new int[Network.NODES_MAX];
        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE;
            previous[i] = -1;
        }

        distances[fromIndex] = 0;
        previous[fromIndex] = fromIndex;

        PriorityQueue<Vertex> candidates = new PriorityQueue<Vertex>();

        for (int i = 0; i < Network.NODES_MAX; i++) {
            if (i != fromIndex && network.isValidRoute(fromIndex, i)) {
                Vertex v = new Vertex(i,1);
                candidates.add(v);
            }
        }

        while (!candidates.isEmpty()) {

            Vertex v = candidates.poll();
            int currentIndex = v.getIndex();
            System.out.printf("Examining index: %c\n",(char)(currentIndex + 'A'));
            distances[currentIndex] = v.getCost();
            if (v.getIndex() == toIndex) {
                break;
            }

            for (int i = 0; i < Network.NODES_MAX; i++) {
                if (i != currentIndex && network.isValidRoute(i, currentIndex) && distances[i] == Integer.MAX_VALUE) {

                    v = null;
                    for (Vertex iterator : candidates) {
                        if (iterator.getIndex() == i) {
                            v = iterator;
                            break;
                        }
                    }

                    if (v != null && (v.getCost() > distances[currentIndex] + 1)) {
                        v.setCost(distances[currentIndex]+1);
                    } else if (v == null) {
                        candidates.add(new Vertex(i,distances[currentIndex] + 1));
                    }

                    previous[i] = currentIndex;

                }
            }

            fromIndex = currentIndex;

        }

        System.out.print("[ShortestHop] distances: ");
        for (int i : distances) {
            if (i != Integer.MAX_VALUE) {
                System.out.print(i + " ");
            } else {
                System.out.print("- ");
            }
        }
        System.out.println();

        System.out.print("              previous : ");
        for (int i : previous) {
            System.out.printf("%c ",(char)(i + 'A'));
        }
        System.out.println();

        ArrayList<Integer> path = new ArrayList<Integer>();
        int index = toIndex;
        while (index != fromIndex) {
            path.add(index);
        }
        path.add(fromIndex);

        return path;

    }

}

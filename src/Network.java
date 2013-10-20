import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 *        Made with Love
 */
public class Network {

    public static final int NODES_MAX = 26;

    private Edge links[][];

    private Network () {
        this.links = new Edge[Network.NODES_MAX][Network.NODES_MAX];
        for (int i = 0; i < links.length; i++) {
            for (int j = 0; j < links[i].length; j++) {
                 links[i][j] = null;
            }
        }
    }

    public static Network generate (char from[], char to[], int propagationDelay[], int circuitCapacity[]) {

        Network n = new Network();

        int numNodes = from.length;
        for (int i = 0; i < numNodes; i++) {
            int translatedFrom = Network.translate(from[i]);
            int translatedTo = Network.translate(to[i]);
            try {
                n.addLink(translatedFrom,translatedTo,propagationDelay[i],circuitCapacity[i]);
            } catch (IllegalArgumentException e) {
                System.out.println ("[Network]: " + e.getMessage());
                System.exit(1);
            }
        }

        return n;

    }

    public void addLink (int from, int to, int propagationDelay, int circuitCapacity) throws IllegalArgumentException {

        if (this.links[from][to] == null) {
            Edge n = new Edge(propagationDelay, circuitCapacity);
            this.links[from][to] = n;
            this.links[to][from] = n;
        } else {
            throw new IllegalArgumentException("Link "+from+"<->"+to+" already exists.");
        }

    }

    public boolean isAdjacent (int from, int to) {
        return (links[from][to] != null);
    }

    public boolean isValidPath (List<Integer> path) {

        boolean isValidRoute = true;

        for (int i = 0; i <path.size()-1; i++) {
            Edge e = this.getEdge(path.get(i), path.get(i+1));
            if (e == null || e.getCurrentLoad() >= e.getCircuitCapacity()) {
                isValidRoute = false;
                break;
            }
        }

        return isValidRoute;

    }

    public Edge getEdge (int from, int to) throws IllegalArgumentException {

        if (from < 0 || from >= Network.NODES_MAX) {
            throw new IllegalArgumentException("[Network] from: "+from+" is illegal.");
        }

        if (to < 0 || to >= Network.NODES_MAX) {
            throw new IllegalArgumentException("[Network] to: "+to+" is illegal.");
        }

        return links[from][to];

    }

    public static int translate (char letter) {
        int translation = -1;
        if (letter >= 'A' && letter <= 'Z') {
            translation = letter - 'A';
        }
        return translation;
    }

    public void printNetwork () {
        for (int i = 0; i < Network.NODES_MAX; i++) {
            for (int j = 0; j < Network.NODES_MAX; j++) {
                if (links[i][j] != null) {
                    System.out.print("("+links[i][j].getPropagationDelay()+","+links[i][j].getCircuitCapacity()+") ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    public Network deepClone () {

        Network clone = new Network();

        for (int i = 0; i < Network.NODES_MAX; i++) {
            for (int j = 0; j < Network.NODES_MAX; j++) {
                if (links[i][j] != null) {
                    int propagationDelay = links[i][j].getPropagationDelay();
                    int circuitCapacity = links[i][j].getCircuitCapacity();
                    clone.addLink(i, j, propagationDelay, circuitCapacity);
                }
            }
        }

        return clone;

    }

}
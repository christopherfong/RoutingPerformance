/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 *        Made with Love
 */
public class Network {

    private static final int NODES_MAX = 26;

    private Node links[][];

    private Network () {
        this.links = new Node[Network.NODES_MAX][Network.NODES_MAX];
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
            int translatedFrom = n.translate(from[i]);
            int translatedTo = n.translate(to[i]);
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
            Node n = new Node(propagationDelay, circuitCapacity);
            this.links[from][to] = n;
            this.links[to][from] = n;
        } else {
            throw new IllegalArgumentException("Link "+from+"<->"+to+" already exists.");
        }

    }

    public int translate (char letter) {
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

}
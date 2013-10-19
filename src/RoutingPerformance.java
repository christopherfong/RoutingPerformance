import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 *        Made with Love
 */
public class RoutingPerformance {

    private Network network;

    public static void main (String args[]) throws Exception {

        if (args.length < 3) {
            System.out.println("Usage: ROUTING_SCHEME TOPOLOGY_FILE WORKLOAD_FILE");
            System.exit(1);
        }

        FileReader fileReader;

        File topologyFile = new File(args[1]);
        LineNumberReader topologyStream = null;
        try {
            fileReader = new FileReader(topologyFile);
            topologyStream = new LineNumberReader(fileReader);
        } catch (FileNotFoundException e) {
            System.out.println ("[RoutingPerformance] Attempted to open "+topologyFile.toString());
            System.out.println ("Exception received: " + e.getMessage());
            System.exit(1);
        }

        Network n = parseTopology(topologyStream);
        RoutingPerformance rp = new RoutingPerformance(n);

    }

    public RoutingPerformance (Network network) {
        this.network = network;
    }

    public static Network parseTopology (LineNumberReader topologyStream) throws IOException {

        ArrayList<Character> from = new ArrayList<Character>();
        ArrayList<Character> to = new ArrayList<Character>();
        ArrayList<Integer> propagationDelay = new ArrayList<Integer>();
        ArrayList<Integer> circuitCapacity = new ArrayList<Integer>();

        while (topologyStream.ready()) {
            String   line  = topologyStream.readLine();
            String[] specs = line.split(" ");

            from.add(specs[0].charAt(0));
            to.add(specs[1].charAt(0));
            propagationDelay.add(Integer.parseInt(specs[2]));
            circuitCapacity.add(Integer.parseInt(specs[3]));
        }

        char[] fromArray = new char[from.size()];
        char[] toArray   = new char[to.size()];
        int[] propArray  = new int [propagationDelay.size()];
        int[] circuitArray = new int [circuitCapacity.size()];
        for (int i = 0; i < from.size(); i++) {
            fromArray[i] = from.get(i);
            toArray[i]   = to.get(i);
            propArray[i] = propagationDelay.get(i);
            circuitArray[i] = circuitCapacity.get(i);
        }

        return Network.generate(fromArray, toArray, propArray, circuitArray);

    }

}

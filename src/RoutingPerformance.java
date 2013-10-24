import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 19/10/13
 * Made with Love
 */
public class RoutingPerformance {

    public static final boolean DEBUG = true;

    public static void main(String args[]) throws Exception {

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
            System.out.println("[RoutingPerformance] Attempted to open " + topologyFile.toString());
            System.out.println("Exception received: " + e.getMessage());
            System.exit(1);
        }

        Network network = parseTopology(topologyStream);

        Algorithm algorithm = null;
        if (args[0].equals("SHP")) {
            algorithm = Algorithm.SHORTEST_HOP;
        } else if (args[0].equals("SDP")) {
            algorithm = Algorithm.SHORTEST_DELAY;
        } else if (args[0].equals("LLP")) {
            algorithm = Algorithm.LEAST_LOAD;
        } else {
            System.out.println("Usage: ROUTING_SCHEME TOPOLOGY_FILE WORKLOAD_FILE");
            System.exit(1);
        }

        PathFinder djikstra = new Djikstra(network, algorithm);

        Simulator simulatee = new Simulator(network, djikstra);

        File workloadFile = new File(args[2]);
        LineNumberReader workloadStream = null;
        try {
            fileReader = new FileReader(workloadFile);
            workloadStream = new LineNumberReader(fileReader);
        } catch (FileNotFoundException e) {
            System.out.println("[RoutingPerformance] Attempted to open " + workloadFile.toString());
            System.out.println("Exception received: " + e.getMessage());
            System.exit(1);
        }

        parseWorkload(workloadStream, simulatee);

        simulatee.run();
        simulatee.printReport();

    }

    public static Network parseTopology(LineNumberReader topologyStream) throws IOException {

        ArrayList<Character> from = new ArrayList<Character>();
        ArrayList<Character> to = new ArrayList<Character>();
        ArrayList<Integer> propagationDelay = new ArrayList<Integer>();
        ArrayList<Integer> circuitCapacity = new ArrayList<Integer>();

        while (topologyStream.ready()) {
            String line = topologyStream.readLine();
            String[] specs = line.split(" ");

            from.add(specs[0].charAt(0));
            to.add(specs[1].charAt(0));
            propagationDelay.add(Integer.parseInt(specs[2]));
            circuitCapacity.add(Integer.parseInt(specs[3]));
        }

        char[] fromArray = new char[from.size()];
        char[] toArray = new char[to.size()];
        int[] propArray = new int[propagationDelay.size()];
        int[] circuitArray = new int[circuitCapacity.size()];
        for (int i = 0; i < from.size(); i++) {
            fromArray[i] = from.get(i);
            toArray[i] = to.get(i);
            propArray[i] = propagationDelay.get(i);
            circuitArray[i] = circuitCapacity.get(i);
        }

        return Network.generate(fromArray, toArray, propArray, circuitArray);

    }

    public static void parseWorkload(LineNumberReader workloadStream, Simulator s) throws IOException {
        while (workloadStream.ready()) {
            String line = workloadStream.readLine();
            String[] specs = line.split(" ");

            Double start = Double.parseDouble(specs[0]);
            int from = Network.translate(specs[1].charAt(0));
            int to = Network.translate(specs[2].charAt(0));
            Double runtime = Double.parseDouble(specs[3]);

            s.addRequest(from, to, start, runtime);
        }
    }

}

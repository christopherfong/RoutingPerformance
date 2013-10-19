import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Chris FONG
 * @since : 20/10/13
 *        Made with Love
 */
public class Simulator {

    private Network network;
    private PathFinder algorithm;
    private Queue<Request> requests;
    private PriorityQueue<Request> running;

    private int numRequests;

    public Simulator (Network network, PathFinder algorithm) {
        this.network = network;
        this.algorithm = algorithm;
        this.requests = new LinkedList<Request>();
        this.running = new PriorityQueue<Request>();

        this.numRequests = 0;

    }

    public void run() {

        double currentTime;
        int success = 0;
        int failures = 0;

        while (!requests.isEmpty()) {
            Request currentRequest = requests.poll();
            currentTime = currentRequest.getStartTime();

            while(!running.isEmpty() && running.peek().getFinishTime() < currentTime) {
                Request completed = running.poll();
                List<Integer> path = completed.getPath();

                System.out.printf("Finishing %c -> %c\n", completed.getFrom()+'A',completed.getTo()+'A');
                for (int i = 0; i < path.size()-1; i++) {
                    Edge e = this.network.getEdge(path.get(i), path.get(i+1));
                    e.lowerLoad();
                }
            }

            List<Integer> path = algorithm.find(currentRequest.getFrom(), currentRequest.getTo());
            if (path != null) {
                currentRequest.setPath(path);
                for (int i = 0; i < path.size()-1; i++) {
                    Edge e = this.network.getEdge(path.get(i), path.get(i+1));
                    e.raiseLoad();
                }
                success++;
                System.out.printf("Starting %c -> %c\n", currentRequest.getFrom()+'A',currentRequest.getTo()+'A');
                this.running.add(currentRequest);
            } else {
                failures++;
            }
        }

        System.out.printf("Total Requests: %d\n", this.numRequests);
        System.out.printf("Successes: %d\n", success);
        System.out.printf("Failures: %d\n", failures);

    }

    public void addRequest (int from, int to, double start, double runtime) {
        Request r = new Request(from, to, start, runtime);
        this.numRequests++;
        this.requests.add(r);
    }

}

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
    private int successes;
    private int failures;

    private int hops;
    private int delay;

    public Simulator (Network network, PathFinder algorithm) {
        this.network = network;
        this.algorithm = algorithm;
        this.requests = new LinkedList<Request>();
        this.running = new PriorityQueue<Request>();

        this.numRequests = 0;
        this.successes = 0;
        this.failures = 0;

        this.hops = 0;
        this.delay = 0;

    }

    public void run() {

        double currentTime;

        while (!requests.isEmpty()) {
            Request currentRequest = requests.poll();
            currentTime = currentRequest.getStartTime();
            if (RoutingPerformance.DEBUG) {
                System.out.printf("Current time: %f\n", currentTime);
            }
            while(!running.isEmpty() && running.peek().getFinishTime() < currentTime) {
                Request completed = running.poll();
                if (RoutingPerformance.DEBUG) {
                    System.out.printf(" Request: %c<->%c finished at %f\n", completed.getFrom()+'A',completed.getTo()+'A', completed.getFinishTime());
                }
                List<Integer> path = completed.getPath();

                for (int i = 0; i < path.size()-1; i++) {
                    Edge e = this.network.getEdge(path.get(i), path.get(i+1));
                    e.lowerLoad();
                    if (RoutingPerformance.DEBUG) {
                        System.out.printf("  Lowering load of %c<->%c to: %d/%d\n", path.get(i)+'A',path.get(i+1)+'A',e.getCurrentLoad(),e.getCircuitCapacity());
                    }
                }
            }

            if (RoutingPerformance.DEBUG) {
                System.out.printf(" Request: %c<->%c starting\n", currentRequest.getFrom()+'A',currentRequest.getTo()+'A');
            }
            List<Integer> path = algorithm.find(currentRequest.getFrom(), currentRequest.getTo());
            if (path != null && network.isValidPath(path)) {
                currentRequest.setPath(path);
                for (int i = 0; i < path.size()-1; i++) {
                    Edge e = this.network.getEdge(path.get(i), path.get(i+1));
                    this.delay += e.getPropagationDelay();
                    e.raiseLoad();
                    if (RoutingPerformance.DEBUG) {
                        System.out.printf("  Raising load of %c<->%c to: %d/%d\n", path.get(i)+'A',path.get(i+1)+'A',e.getCurrentLoad(),e.getCircuitCapacity());
                    }
                }
                this.hops += path.size();
                this.successes++;
                this.running.add(currentRequest);
            } else {
                this.failures++;
            }
        }

    }

    public void addRequest (int from, int to, double start, double runtime) {
        Request r = new Request(from, to, start, runtime);
        this.numRequests++;
        this.requests.add(r);
    }

    public void printReport () {
        System.out.printf("Total number of virtual circuit requests: %d\n", this.numRequests);
        System.out.printf("Number of successfully routed requests: %d\n", this.successes);
        System.out.printf("Percentage of successfully routed requests: %.2f\n", ((double)this.successes/this.numRequests)*100);

        System.out.printf("Number of blocked requests: %d\n",this.failures);
        System.out.printf("Percentage of blocked requests: %.2f\n",(double)this.failures/this.numRequests*100);

        System.out.printf("Average number of hops per circuit: %.2f\n",(double)this.hops/this.successes);
        System.out.printf("Average cumulative propagation delay per circuit %.2f\n",(double)this.delay/this.successes);
    }

}

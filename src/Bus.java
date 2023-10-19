import java.util.concurrent.Semaphore;

public class Bus implements Runnable {
    public static final int interArrivalTime = 20 * 60;
    private Semaphore busArrivalSemaphore;
    private Semaphore ridersBoardSemaphore;
    private Semaphore ridersCountSemaphore;
    private BusStop busStop;

    public Bus(Semaphore busArrivalSemaphore, Semaphore ridersBoardSemaphore, Semaphore ridersCountSemaphore, BusStop busStop) {
        this.busArrivalSemaphore = busArrivalSemaphore;
        this.ridersBoardSemaphore = ridersBoardSemaphore;
        this.ridersCountSemaphore = ridersCountSemaphore;
        this.busStop = busStop;
    }

    @Override
    public void run() {
        try {
            arrive();
            ridersCountSemaphore.acquire();
            if (busStop.getRiders() > 0) {
                startBoarding();
                busArrivalSemaphore.release();
                waitRidersBoard();
                ridersBoardSemaphore.acquire();
            }
            ridersCountSemaphore.release();
            depart();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitRidersBoard() {
        System.out.println("Bus "+Thread.currentThread().getId()+": wait till riders boarding...");
    }

    private void startBoarding() {
        System.out.println("Bus "+Thread.currentThread().getId()+": start boarding...");
    }

    public void arrive() {
        System.out.println("Bus "+Thread.currentThread().getId()+": arrive");
    }

    public void depart() {
        System.out.println("Bus "+Thread.currentThread().getId()+": depart");
    }
}

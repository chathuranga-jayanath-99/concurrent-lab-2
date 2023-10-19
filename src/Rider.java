import java.util.concurrent.Semaphore;

public class Rider implements Runnable {
    public static final int interArrivalTime = 30;
    private BusStop busStop;
    private Semaphore busArrivalSemaphore;
    private Semaphore riderBoardSemaphore;
    private Semaphore riderWaitSemaphore;
    private Semaphore riderCountSemaphore;

    public Rider(BusStop busStop, Semaphore busArrivalSemaphore, Semaphore riderBoardSemaphore, Semaphore riderWaitSemaphore, Semaphore riderCountSemaphore) {
        this.busStop = busStop;
        this.busArrivalSemaphore = busArrivalSemaphore;
        this.riderBoardSemaphore = riderBoardSemaphore;
        this.riderWaitSemaphore = riderWaitSemaphore;
        this.riderCountSemaphore = riderCountSemaphore;
    }

    @Override
    public void run() {
        try {
            riderWaitSemaphore.acquire();
            startWaitingForQueue();
            riderCountSemaphore.acquire();
            busStop.increaseRiders();
            riderCountSemaphore.release();
            startWaitingForBus();
            busArrivalSemaphore.acquire();
            boardBus();
            busStop.decreaseRiders();
            if (busStop.getRiders() == 0) {
                riderBoardSemaphore.release();
            } else {
                busArrivalSemaphore.release();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void stopBoarding() {
        System.out.println("Stop taking more riders");
    }

    private void startWaitingForQueue() {
        System.out.println("Rider " + Thread.currentThread().getId() + ": waiting for queue");
    }

    private void startWaitingForBus() {
        System.out.println("Rider " + Thread.currentThread().getId() + ": waiting for bus");
    }

    public void boardBus() {
        System.out.println("Rider " + Thread.currentThread().getId() + ": board to bus");
    }
}

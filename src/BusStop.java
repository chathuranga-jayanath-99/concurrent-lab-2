import java.util.concurrent.Semaphore;

public class BusStop {
    private int riders = 0;
    private Semaphore riderCountSemaphore;
    private Semaphore waitRiderSemaphore;
    private Semaphore busSemaphore;
    private Semaphore allBoardSemaphore;

    public BusStop() {
        riderCountSemaphore = new Semaphore(1);
        waitRiderSemaphore = new Semaphore(50);
        busSemaphore = new Semaphore(0);
        allBoardSemaphore = new Semaphore(0);
    }

    public int getRiders() {
        return riders;
    }

    public void increaseRiders() {
        riders++;
    }

    public void decreaseRiders() {
        riders--;
    }

    public Semaphore getRiderCountSemaphore() {
        return riderCountSemaphore;
    }

    public Semaphore getWaitRiderSemaphore() {
        return waitRiderSemaphore;
    }

    public Semaphore getBusSemaphore() {
        return busSemaphore;
    }

    public Semaphore getAllBoardSemaphore() {
        return allBoardSemaphore;
    }
}

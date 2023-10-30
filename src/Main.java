import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static long calculateExponentialDelay(double mean) {
        Random random = new Random();
        return (long) (-mean * Math.log(1 - random.nextDouble()));
    }

    public static void main(String[] args) throws InterruptedException {
        BusStop busStop = new BusStop();
        Thread riderSpawnerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Thread thread = new Thread(new Rider(busStop, busStop.getBusSemaphore(), busStop.getAllBoardSemaphore(), busStop.getWaitRiderSemaphore(), busStop.getRiderCountSemaphore()));
                    thread.start();
                    try {
                        Thread.sleep(calculateExponentialDelay(Rider.interArrivalTime));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        Thread busSpawnerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Thread thread = new Thread(new Bus(busStop.getBusSemaphore(), busStop.getAllBoardSemaphore(), busStop.getRiderCountSemaphore(), busStop));
                    thread.start();
                    try {
                        Thread.sleep(calculateExponentialDelay(Bus.interArrivalTime));
                    } catch (Exception e) {

                    }
                }
            }
        });
        riderSpawnerThread.setDaemon(true);
        busSpawnerThread.setDaemon(true);
        riderSpawnerThread.start();
        busSpawnerThread.start();

        Thread.sleep(50000);
        System.out.println("Main thread finishes");
    }
}
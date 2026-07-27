import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

class DiningPhilosophers {
    private final ReentrantLock[] forks = new ReentrantLock[5];
    private final Semaphore limit = new Semaphore(4);

    public DiningPhilosophers() {
        for (int i = 0; i < 5; i++) {
            forks[i] = new ReentrantLock();
        }
    }

    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        
        int leftFork = philosopher;
        int rightFork = (philosopher + 1) % 5;

        
        limit.acquire();

        forks[leftFork].lock();
        forks[rightFork].lock();

        try {
            pickLeftFork.run();
            pickRightFork.run();
            eat.run();
            putLeftFork.run();
            putRightFork.run();
        } finally {
            forks[rightFork].unlock();
            forks[leftFork].unlock();
            limit.release();
        }
    }
}

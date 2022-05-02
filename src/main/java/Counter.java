import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        final Lock lock = new ReentrantLock();
        Thread thread1 = new Thread(() -> Counter.printCount(lock));
        Thread thread2 = new Thread(() -> Counter.printCount(lock));
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final value is " + count);
    }

    private static void printCount(Lock lock) {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            count++;
            lock.unlock();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

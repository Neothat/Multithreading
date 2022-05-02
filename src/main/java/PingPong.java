public class PingPong {
    private final Object monitor = new Object();
    private volatile String currentSound = "ping";

    public static void main(String[] args) {
        PingPong pingPong = new PingPong();
        Thread thread1 = new Thread(pingPong::ping);
        Thread thread2 = new Thread(pingPong::pong);
        thread1.start();
        thread2.start();
    }

    public void ping() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 10; i++) {
                    while (!currentSound.equals("ping")) {
                        monitor.wait();
                    }
                    System.out.println("ping");
                    currentSound = "pong";
                    monitor.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pong() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 10; i++) {
                    while (!currentSound.equals("pong")) {
                        monitor.wait();
                    }
                    System.out.println("pong");
                    currentSound = "ping";
                    monitor.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


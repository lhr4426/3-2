public class ThreadSyncPractice {

    public static void main(String[] args) {

// TODO Auto-generated method stub

        ThreadB b = new ThreadB();

// b.start();

        System.out.println("1");

        synchronized (b) {

            try {

                b.start();

                System.out.println("Waiting for b to complete...");

                System.out.println("2");

                b.wait();

                System.out.println("3");

                try {

                    b.join(); // 어떤 스레드가 끝난 후에 진행해야 할 때 씀

                } catch (InterruptedException e) {

//// TODO Auto-generated catch block

                    e.printStackTrace();

                }

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

            System.out.println("Total is: " + b.total);

        }

    }

}

class ThreadB extends Thread {

    int total;

    @Override

    public void run() {

        synchronized (this) {

            for (int i = 0; i < 1000; i++) {

                total += i;

            }

            System.out.println("4");

            notify();

            System.out.println("5");

// notifyAll();

        }

    }

}

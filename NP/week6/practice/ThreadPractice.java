public class ThreadPractice {
    public static void main(String[] args) {

        // a를 100번 프린트하는 스레드
        Thread printA = new PrintChar('a', 100);

        // b를 100번 프린트하는 스레드
        Thread printB = new PrintChar('b', 100);

        // 1에서 100까지 프린트하는 스레드 (Runnable)
        Runnable print100 = new PrintNum(100);

        //PrintNum print100 = new PrintNum(100);

        Thread thread3 = new Thread(print100);

        printA.start();
        thread3.start();

//        printA.run(); // 이러면 안됩니다.

        printB.start();



    }

}

class PrintChar extends Thread { // Thread class 상속

    private char charToPrint;

    private int times;

    public PrintChar(char c, int t) {

        charToPrint = c;

        times = t;

    }

    @Override

    public void run() {
        for (int i = 0; i < times; i++) {
            System.out.print(charToPrint);
        }
    }
}

class PrintNum implements Runnable { // Runnable interface 구현

    private int lastNum;

    public PrintNum(int n) {

        lastNum = n;

    }

    public void run() {
        Thread thread4 = new Thread(new PrintChar('c', 100));
        thread4.start();
        try {
            for (int i = 1; i <= lastNum; i++) {
                System.out.print(i);
//                if (i == 10)
//                    Thread.sleep(5000); // sleep 연습
                if (i == 1)
                    thread4.join(); // join 연습
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

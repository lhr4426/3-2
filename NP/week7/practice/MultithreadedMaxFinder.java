import java.util.concurrent.*;

public class MultithreadedMaxFinder {
    public static int max(int[] data) throws InterruptedException, ExecutionException {
        if (data.length == 1) {
            return data[0];
        } else if (data.length == 0) {
            throw new IllegalArgumentException();
        }
        // split the job into 2 pieces
        FindMaxTask task1 = new FindMaxTask(data, 0, data.length / 2);
        FindMaxTask task2 = new FindMaxTask(data, data.length / 2, data.length);
        // spawn 2 threads
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future1 = service.submit(task1);
        Future<Integer> future2 = service.submit(task2);

        service.shutdown(); // 주어진 일까지만 시키고 멈춤

        return Math.max(future1.get(), future2.get()); // get은 future에 값이 들어올 때 까지 알아서 기다림
    }

    public static void main(String[] args) {

        int[] samples = new int[10000];
        for (int i = 0; i < 10000; i++)
            samples[i] = (int) (Math.random() * 10000);
//System.out.println(Arrays.toString(samples));
        int maxReturn;
        try {
            maxReturn = max(samples);
            System.out.println(" Max: = " + maxReturn);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
package homework;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class homework_2 {
    private final static int NUM_THREADS = 16;

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        Queue<LogEntry> results = new LinkedList<LogEntry>();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("access_log_23.txt"), "UTF-8"))){
            for (String entry = in.readLine(); entry != null; entry = in.readLine()) {
                LookupTask task = new LookupTask(entry);
                Future<String> future = executor.submit(task);
                LogEntry result = new LogEntry(entry, future);
                results.add(result);
            }
        }

        try(OutputStream of = new FileOutputStream("access_hosts.txt");
            OutputStreamWriter ow = new OutputStreamWriter(of, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(ow)){ // 파일 열기
            for (LogEntry result : results) {
                try {
                    System.out.println(result.future.get()); // 콘솔
                    bw.write(result.future.get()); // 파일
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(result.original);
                    bw.write(result.original);
                }
                bw.newLine(); // 개행
            }
            bw.flush(); // 버퍼 정리
        }
        executor.shutdown();
    }
    private static class LogEntry {
        String original; // 원래 있던 내용
        Future<String> future;

        LogEntry(String original, Future<String> future) {
            this.original = original;
            this.future = future;
        }
    }
}


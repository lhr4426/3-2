package practice;

import java.util.concurrent.Callable;
import java.net.*;

public class LookupTask implements Callable<String> {

    private String line;

    public LookupTask(String line) {
        this.line = line;
    }

    @Override
    public String call() throws Exception {
        try {
            int index = line.indexOf(' '); // 첫 번째 나타나는 공백 위치
            String address = line.substring(0, index); // substring은 0부터 index - 1 까지 위치 문자열 가져옴

            String theRest = line.substring(index);
            String hostname = InetAddress.getByName(address).getHostName();
            return hostname + " " + theRest;
        } catch (Exception ex) {
            return line; // 문제 생기면 그냥 그 줄 그대로 뱉어냄
        }
    }
    public static final String BLACKHOLE = "zen.spamhaus.org";
}

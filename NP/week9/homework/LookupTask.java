package homework;

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

            if (isSpammer(hostname))
                return hostname + "-SPAM-" + theRest;
            else
                return hostname + " " + theRest;
        } catch (Exception ex) {
            return line; // 문제 생기면 그냥 그 줄 그대로 뱉어냄
        }
    }
    public static final String BLACKHOLE = "zen.spamhaus.org";
    private static boolean isSpammer(String arg) {
        try {
            InetAddress address = InetAddress.getByName(arg);
            byte[] quad = address.getAddress(); // byte array 로 뽑아옴
            String query = BLACKHOLE;
            for (byte octet : quad) {
                int unsignedByte = octet < 0 ? octet + 256 : octet;
                query = unsignedByte + "." + query; // 쿼리를 내 뒤에 붙임 : 순서가 거꾸로가 됨
            }
            InetAddress ad = InetAddress.getByName(query);
            // 결과가 있으면 스팸임
            return true;
        } catch (UnknownHostException e) {
            // 결과가 없으면 스팸이 아님
            return false;
        }
    }
}

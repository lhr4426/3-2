package practice;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpOptionMethod {
    public static void main(String[] args) {
        try {
            //URL u = new URL("http://www.ibiblio.org/xml");
            // URL u = new URL("http://www.hanbat.ac.kr");
            URL u = new URL("https://www.oreilly.com");
            // URL u = new URL("http://cyber.hanbat.ac.kr");

            // 원래 openConnection의 반환값은 URLConnection 인데 타입캐스팅 해서 HttpURLConnection으로 바꿈
            HttpURLConnection http = (HttpURLConnection) u.openConnection();

            // HttpURLConnection 내부에는 Reqeust 메소드를 바꿀 수 있는 기능이 있다.
            // http.setRequestMethod("HEAD");
            http.setRequestMethod("OPTIONS");

            for (int j = 0; ; j++) {
                String header = http.getHeaderField(j);
                if (header == null) break;
                System.out.println(http.getHeaderFieldKey(j) + ": " + header);
            }

//System.out.println(u + " was last modified at " + new Date(http.getLastModified()));
        } catch (MalformedURLException ex) {
            System.err.println("This is not a URL I understand");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println();
    }
}
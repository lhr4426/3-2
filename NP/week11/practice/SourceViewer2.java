package practice;

import java.io.*;
import java.net.*;

public class SourceViewer2 {
    public static void main(String[] args) {
        try {
            // Open the URLConnection for reading
            URL u = new URL("http://www.hanbat.ac.kr");
            URLConnection uc = u.openConnection();
            try (InputStream raw = uc.getInputStream()) { // autoclose
                InputStream buffer = new BufferedInputStream(raw);
                // chain the InputStream to a Reader
                // 이 페이지가 뭘로 되어있는지 모름 -> response 헤더에서 알아내는게 베스트
                Reader reader = new InputStreamReader(buffer, "UTF-8");
                int c;
                while ((c = reader.read()) != -1) {
                    System.out.print((char) c);
                }
            }
        } catch (MalformedURLException ex) {
            System.err.println(args[0] + " is not a parseable URL");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}

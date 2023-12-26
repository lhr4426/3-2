package pratice;

import java.io.*;
import java.net.*;

public class SourceViewer {
    public static void main(String[] args) {

        InputStream in = null;
        try {
            // Open the URL for reading
            URL u = new URL("http://www.hanbat.ac.kr");  // 한밭대학교 첫 페이지의 html 가져오기 위함
            in = u.openStream(); // URL의 스트림을 열기
// buffer the input to increase performance in = new BufferedInputStream(in);
// chain the InputStream to a Reader
            Reader r = new InputStreamReader(in); // Reader로 가져오기
            int c;
            while ((c = r.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (MalformedURLException ex) {
            System.err.println(args[0] + " is not a parseable URL");
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) { // ignore
                }
            }
        }
    }
}

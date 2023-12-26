package pratice;

import java.io.*;
import java.net.*;

public class ContentGetter {
    public static void main(String[] args) {
        // Open the URL for reading
        try {
            URL u = new URL("http://www.hanbat.ac.kr");
            Object o = u.getContent();
            System.out.println("I got a " + o.getClass().getName());
        } catch (MalformedURLException ex) {
            System.err.println(" is not a parseable URL");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}

package homework;
import java.net.*;
public class homework_1 {
    public static void main(String[] args) {
        try{
            InetAddress address = InetAddress.getByName("www.google.com");
            System.out.println(address);
            System.out.println("Host name : " + address.getHostName());
            System.out.println("Canonical Host name : " + address.getCanonicalHostName());

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}

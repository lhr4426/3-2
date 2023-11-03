package practice;
import java.net.*;
public class Chap4_7_1_IBiblioAddress {
    public static void main(String args[]) {
        try {
            InetAddress ibiblio = InetAddress.getByName("www.google.com");
            System.out.println(ibiblio);

            String Canonicalibiblio = ibiblio.getCanonicalHostName();

            System.out.println(Canonicalibiblio);

            InetAddress helios = InetAddress.getByName(Canonicalibiblio);

            System.out.println(helios);

            if (ibiblio.equals(helios)) {
                System.out.println("Same");
            } else {
                System.out.println("They are different.");
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}

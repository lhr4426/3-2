package practice;
import java.net.*;
public class Chap4_5_1_AddressTest {

    public static int getVersion(InetAddress ia) {
        byte[] address = ia.getAddress();

        if(address.length == 4)
            return 4;
        else if(address.length == 16)
            return 6;
        else
            return -1;
    }



    public static void main(String[] args) {
        InetAddress ia;
        try {
            ia = InetAddress.getByName("www.hanbat.ac.kr");
            byte[] address = ia.getAddress();
            for (byte b : address)
                System.out.println(b);
            // 이거 찍으면 음수 나오는데 이유는 address byte는 unsigned이고 자바에서 돌릴땐 signed로 돌아가서 그럼
            switch (getVersion(ia)) {
                case 4:
                    System.out.println("IPv4");
                    break;
                case 6:
                    System.out.println("IPv6");
                    break;
                default:
                    System.out.println("Nothing");
            }


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}

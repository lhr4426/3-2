package practice;
import java.net.*;
public class Chap4_9_1_SpamCheck {
    // 스팸인지 아닌지 알려주는 사이트
    // 원래 아이피를 거꾸로 뒤집은 뒤에 주소를 넣으면 스팸인지 아닌지 알 수 있다
    // ex. 1.2.3.4 라는 IP 주소가 스팸 사이트인지 아닌지 알고싶으면
    // 4.3.2.1.zen.spamhaus.org 해서 getByName으로 물어보면 됨
    // 응답이 있으면 스팸, 응답이 없으면 스팸이 아님
    public static final String BLACKHOLE = "zen.spamhaus.org";

    public static void main(String[] args) {
        args = new String[] {"www.hanbat.ac.kr", "5.199.130.188"};
        for (String arg : args) {
            if (isSpammer(arg)) {
                System.out.println(arg + " is a know spammer.");
            } else {
                System.out.println(arg + " appears legitimate."); // legitimate : 합법
            }
        }
    }

    private static boolean isSpammer(String arg) {
        try {
            InetAddress address = InetAddress.getByName(arg);
            System.out.println(address);
            byte[] quad = address.getAddress(); // byte array 로 뽑아옴
            for (byte b : quad)
                System.out.println(b);

            String query = BLACKHOLE;
            for (byte octet : quad) {
                int unsignedByte = octet < 0 ? octet + 256 : octet;

                query = unsignedByte + "." + query; // 쿼리를 내 뒤에 붙임 : 순서가 거꾸로가 됨
            }
            System.out.println("Query = " + query);

            InetAddress ad = InetAddress.getByName(query);
            System.out.println(ad); 
            
            // 결과가 있으면 스팸임
            
            return true;

        } catch (UnknownHostException e) {
            System.out.println("No Spam!");
            
            // 결과가 없으면 스팸이 아님
            
            return false;
        }
    }
}

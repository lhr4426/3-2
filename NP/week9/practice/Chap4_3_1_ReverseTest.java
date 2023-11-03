package practice;

import java.net.*;

public class Chap4_3_1_ReverseTest {
    public static void main(String[] args){
        try{
            InetAddress ia = InetAddress.getByName("223.194.192.38");
            // 역으로 물어볼 때에는 답이 없을 수도 있다

            System.out.println(ia);
            System.out.println(ia.getCanonicalHostName());
            System.out.println(ia.getHostName());

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}

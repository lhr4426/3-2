package practice;

import java.net.*;

public class chap4_1_1_OreillyByName {

    public static void main(String[] args) {
        try{
            InetAddress address = InetAddress.getByName("www.hanbat.ac.kr");

            System.out.println(address);
            System.out.println("Host name : " + address.getHostName());
            System.out.println("Canonical Host name : " + address.getCanonicalHostName());
            // canonical : 정식의
            // canonical host name : 정식 호스트 이름
            // alias 말고 진짜 정식 이름
            // canonical host name을 물어볼 때는 다시 DNS에다가 물어보는거임
            // 왜냐, getHostName으로 가져오면 이게 별명인지 아니면 정식명인지 모르기 때문에

            InetAddress address2 = InetAddress.getByName("223.194.192.38");
            System.out.println(address2.getHostName());

//            System.out.println(InetAddress.getByName("www.ac.ac.ac")); // 에러 O
            // 이런 이름의 도메인 이름이 없으면 에러

            System.out.println(InetAddress.getByName("123.123.123.123")); // 에러 x
            System.out.println(InetAddress.getByName("123.123.123.123").getHostName()); // 에러 x

//            System.out.println(InetAddress.getByName("123.1.1.300")); // 에러 O
            // IP주소가 형식에 안맞으면 에러

            InetAddress[] addresses = InetAddress.getAllByName("www.naver.com");
            for (InetAddress address3 : addresses) {
                System.out.println(address3);
                System.out.println(address3.getCanonicalHostName());
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}

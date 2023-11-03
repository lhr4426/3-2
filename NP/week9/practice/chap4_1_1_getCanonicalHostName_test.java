package practice;

import java.net.*;
public class chap4_1_1_getCanonicalHostName_test {
    public static void main(String[] args) {
        try{
            InetAddress address = InetAddress.getByName("www.hanbat.ac.kr");

            System.out.println(address);
            System.out.println("Host Name : " + address.getHostName());
            System.out.println("Canonical Host Name : " + address.getCanonicalHostName());
            // reverse가 무조건 온다는 보장은 없음. 왜냐하면 DNS를 어떤 거를 쓰냐에 따라 다르니까
            // WireShark에서 PTR 붙으면 역으로 물어본다는 의미 (Pointer)
            // IP 주소를 주고 이거에 맞는 Domain Name을 알려달라고 할때는 PTR 타입이라고 붙는다

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}

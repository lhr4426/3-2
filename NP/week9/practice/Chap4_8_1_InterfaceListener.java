package practice;
import java.net.*;
import java.util.Enumeration;

public class Chap4_8_1_InterfaceListener {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        // 현재 컴퓨터에 연결되어있는 네트워크 인터페이스를 모두 불러옴
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            System.out.println(ni);
        }
    }
}

package practice;
import java.net.*;
public class Chap4_6_1_IPCharacteristics {
    public static void main(String[] args) {
        String[] temp = {"www.hanbat.ac.kr", "0.0.0.0", "127.0.0.1", "192.168.1.2", "10.0.0.3",
                        "232.0.0.7", "FE80::1234:1234:1234:1234"};

        // FE80으로 시작하는거는 IPv6에서 IP 주소를 할당하지 않았을 때, 연결된 애들끼리 사용가능하도록 알아서 주는 주소
        // 그래서 Link-local임
        
        for (String ad : temp) {
            try{
                InetAddress address = InetAddress.getByName(ad);
                if (address.isAnyLocalAddress()) { // 전부 0인 주소
                    System.out.println(address + " is a wildcard address.");
                }
                if (address.isLoopbackAddress()) { // 127.0.0.1
                    System.out.println(address + " is loopback address.");
                }
                if (address.isLinkLocalAddress()) { // 두 개를 이어줄때 사용
                    System.out.println(address + " is a link-local address.");
                } else if (address.isSiteLocalAddress()) { // 192.168... 이런거는 site local (공유기)
                    System.out.println(address + " is a site-local address.");
                } else { // 외부에서도 공개적으로 사용할 수 있는 주소
                    System.out.println(address + " is a global address.");
                }

                if (address.isMulticastAddress()) { // 일대다 통신 (받는 사람을 한정함. 브로드캐스트와는 다르다)
                    if (address.isMCGlobal()) {
                        System.out.println(address + " is a global multicast address.");
                    } else if (address.isMCOrgLocal()) {
                        System.out.println(address + " is an organization wide multicast address.");
                    } else if (address.isMCSiteLocal()) {
                        System.out.println(address + " is a site wide multicast address.");
                    } else if (address.isMCLinkLocal()) {
                        System.out.println(address + " is a subnet wide multicast address.");
                    } else if (address.isMCNodeLocal()) {
                        System.out.println(address + " is an interface-local multicast address.");
                    } else {
                        System.out.println(address + " is an unknown multicast addres type.");
                    }
                } else { // 일대일 통신
                    System.out.println(address + " is a unicast address.");
                }

            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

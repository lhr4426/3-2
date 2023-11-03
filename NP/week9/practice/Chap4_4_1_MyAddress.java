package practice;

import java.net.*;
public class Chap4_4_1_MyAddress {
    public static void main(String[] args) {
        try {
            InetAddress me = InetAddress.getLocalHost();
            String dottedQuad = me.getHostAddress(); // return type = String
            System.out.println("My Address is " + dottedQuad);
            System.out.println(me);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}

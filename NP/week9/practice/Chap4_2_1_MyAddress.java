package practice;

import java.net.*;

public class Chap4_2_1_MyAddress {
    public static void main(String[] args){
        try{
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}

import java.io.FileInputStream;

import java.io.IOException;

import java.security.DigestInputStream;

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;

//import javax.xml.bind.DatatypeConverter;

public class DigestPractice extends Thread {

    private String filename;

    public DigestPractice(String filename) {

        this.filename = filename;

    }

    @Override

    public void run() {

        try {

            FileInputStream in = new FileInputStream(filename);

            MessageDigest sha = MessageDigest.getInstance("SHA-256"); // sha-256 방법으로 해시화

            DigestInputStream din = new DigestInputStream(in, sha);

            while (din.read() != -1)				;

            din.close();

            byte[] digest = sha.digest();

            StringBuilder result = new StringBuilder(filename);

            result.append(": ");

            //result.append(DatatypeConverter.printHexBinary(digest));

            result.append(byteToHex(digest));

            System.out.println(result);



        } catch (IOException | NoSuchAlgorithmException ex) {

            System.err.println(ex);

        }

    }

    public static void main(String[] args) {



        String[] temp = {"data.txt", "data.bin"}; // 해시화 하려는거



        for (String filename : temp) {

            Thread t = new DigestPractice(filename); // gabriel

            t.start();

        }

    }



    public static String byteToHex(byte[] bytes) {

        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {

            String st = String.format("%02X", b);

            sb.append(st);

        }



        return sb.toString();

    }

}
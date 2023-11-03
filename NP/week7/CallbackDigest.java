import java.io.*;
import java.security.*;

public class CallbackDigest implements Runnable {
    private String filename;

    public CallbackDigest(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            FileInputStream in = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);
            while (din.read() != -1) ; // read entire file din.close();
            byte[] digest = sha.digest();

            // static으로 선언했기 때문에 가능함
            // static method는 클래스 명이 없어도 부를 수 있음 (인스턴스 없이)
            // 문제 : 인스턴스 별로 만들어지는 메소드가 아니다보니 특정 인스턴스한테 답을 줄수가 없음
            // 문제를 해결하기 위해서는 해당 스레드를 부른 클래스 인스턴스를 알아내야함!
            // == 인스턴스 콜백

            CallbackDigestUserInterface.receiveDigest(digest, filename);

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex);
        }
    }
}
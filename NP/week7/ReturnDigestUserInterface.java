// import javax.xml.bind.*; // for DatatypeConverter public class ReturnDigestUserInterface {

public class ReturnDigestUserInterface {
    public static void main(String[]args){

        String[] temp = {"big_data.txt", "big_data2.txt", "big_data3.txt"};

        for(String filename:temp){
            // Calculate the digest
            ReturnDigest dr=new ReturnDigest(filename);dr.start();
            // Now print the result
            StringBuilder result=new StringBuilder(filename);
            result.append(": ");
            byte[] digest = dr.getDigest(); // 너무 빨리 결과값을 내라고 했음. 해당 스레드가 끝났는지 모름

//            result.append(DatatypeConverter.printHexBinary(digest));
            result.append(byteToHex(digest));
            System.out.println(result);
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


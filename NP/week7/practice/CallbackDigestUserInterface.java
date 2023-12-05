public class CallbackDigestUserInterface {
    public static void receiveDigest(byte[] digest, String name) {
        StringBuilder result = new StringBuilder(name);
        result.append(": ");
//        result.append(DatatypeConverter.printHexBinary(digest));
        result.append(byteToHex(digest));
        System.out.println(result);
    }

    public static void main(String[] args) {
        String[] temp = {"txt1.txt", "txt2.txt", "txt3.txt", "txt4.txt", "txt5.txt" };

        for (String filename : temp) {
            // Calculate the digest
            CallbackDigest cb = new CallbackDigest(filename);
            Thread t = new Thread(cb);
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

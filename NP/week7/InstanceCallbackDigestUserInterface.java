public class InstanceCallbackDigestUserInterface {
    private String filename;
    private byte[] digest;

    public InstanceCallbackDigestUserInterface(String filename) {
        this.filename = filename;
    }

    public void calculateDigest() {
        // callback 인자로 this를 줌으로써 인스턴스화 된 객체 본인을 넘겨줌
        // 그러면 나중에 반환받을 때 처음에 불렀던 클래스 인스턴스한테 돌려줄 수 있음!

        InstanceCallbackDigest cb = new InstanceCallbackDigest(filename, this);
        Thread t = new Thread(cb);
        t.start();
    }

    void receiveDigest(byte[] digest) {
        this.digest = digest;
        System.out.println(this); // 이거 하면 밑에 있는 오버라이드 된 toString()이 불림
    }

    @Override
    public String toString() {

        String result = filename + ": ";
        if (digest != null) {
//            result += DatatypeConverter.printHexBinary(digest);
            result += byteToHex(digest);
        } else {
            result += "digest not available";
        }
        return result;
    }

    public static void main(String[] args) {
        String[] temp = {"txt1.txt", "txt2.txt", "txt3.txt", "txt4.txt", "txt5.txt" };

        for (String filename : temp) {
            // Calculate the digest
            InstanceCallbackDigestUserInterface d
                    = new InstanceCallbackDigestUserInterface(filename);
            d.calculateDigest();
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
package homework;

import java.io.*;

public class Problem_3 {
    static final String dataFile = "data.bin";

    public static void main(String[] args){

        try(DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)))){
            out.writeInt(1);
            out.writeDouble(1.0);
            out.writeUTF("abc한밭");
            out.writeInt(2);
            out.writeDouble(2.0);
            out.writeUTF("abc대학교");
        }catch (IOException e) {
            System.err.println(e.getMessage());
        }

//        try(DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile)))) {
//            System.out.format("%d\n", in.readInt());
//            System.out.format("%f\n", in.readDouble());
//            System.out.format("%s\n", in.readUTF());
//            System.out.format("%d\n", in.readInt());
//            System.out.format("%f\n", in.readDouble());
//            System.out.format("%s\n", in.readUTF());
//        }catch (IOException e) {
//            System.err.println(e.getMessage());
//        }

        try(DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile)))) {
            System.out.format("%f\n", in.readDouble());
            System.out.format("%d\n", in.readInt());
            System.out.format("%s\n", in.readUTF());
            System.out.format("%f\n", in.readDouble());
            System.out.format("%d\n", in.readInt());
            System.out.format("%s\n", in.readUTF());
        }catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }


}

package org.example;

import java.io.*;

public class DataOutputStream_test {
    static final String dataFile = "invoice.bin";
    static final int[] units = { 12, 8, 13, 29, 50 }; // 4 byte
    static final double[] prices = { 19.99, 9.99, 15.99, 3.99, 4.99 }; // 8 byte
    static final String[] descs = { "shoes", "watch", "cloth", "socks", "notes" }; // 5 byte (+ 2 byte) -> 7 byte

    // 5 * ( 4 + 8 + 7 ) = 95 byte
    public static void main(String[] args) throws IOException {

        try ( DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)))){
            //int totalLength = 0;
            for (int i = 0; i < prices.length; i++) {
                out.writeInt(units[i]);
                out.writeDouble(prices[i]);
                // out.writeShort(units[i]);
                out.writeUTF(descs[i]);
                //totalLength += descs[i].length();
            }
            //System.out.println("string length=" + totalLength);
        }

        double total = 0.0;
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile)))){
            double price;
            int unit;
            String desc;
            try {
                while (true) {
                    unit = in.readInt();
                    price = in.readDouble();
                    // unit=in.readShort();
                    desc = in.readUTF();
                    System.out.format("You ordered %d units of %s at $%.2f%n", unit, desc, price);
                    total += unit * price;
                }
            } catch (EOFException e) {
            }
            System.out.format("For a TOTAL of: $%.2f%n", total);
        }
    }
}
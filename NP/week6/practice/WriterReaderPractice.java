import java.io.*;

public class WriterReaderPractice {
    public static void main(String[] args) {

        try (OutputStream outFile = new FileOutputStream("dataWriter.txt"); // output stream을 파일 스트림으로 설정

             // output stream writer > output stream (maybe interface?) > file output stream
             OutputStreamWriter outWriter = new OutputStreamWriter(outFile, "utf-8")) {

            outWriter.write("한밭test");

        } catch (IOException ex) {

            System.err.println(ex.getMessage());

        }

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out, "utf-8"))) {

            out.write("한밭test");

        } catch (IOException ex) {

            System.err.println(ex.getMessage());

        }


        try (FileReader in = new FileReader("dataWriter.txt"); // No encoding!

             FileWriter out = new FileWriter("dataout.txt")

        ) {
            int c;

            while ((c = in.read()) != -1) {

                out.write(c);

                System.out.println((char) c);

            }

//            out.write("test");

            System.out.println(in.getEncoding()); // current encoding

            System.out.println(out.getEncoding()); // current encoding

        } catch (IOException ex) {

            System.err.println(ex.getMessage());

        }

    }
}
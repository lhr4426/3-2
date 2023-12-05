package org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        // < Output Stream 실습 >
//        try{
//
//            // 콘솔 스트림으로 출력하기
////            generateCharacters(System.out);
//
//            // 콘솔 스트림으로 버퍼를 이용해 한번에 출력하기
////            generateCharactersBuf(System.out);
//
            // Output Stream을 파일 스트림으로 변경하기
//            OutputStream out = new FileOutputStream("data.txt");
//            generateCharactersBuf(out);
//
//
//        } catch (IOException e){
//            System.err.println(e.getMessage());
//        }

        // < Input Stream 실습 >
        try (InputStream in = new FileInputStream("data.txt")){

            int bytesRead = 0;
            int bytesToRead = 1024;
            byte[] input = new byte[bytesToRead];
            while (bytesRead < bytesToRead) {
                int result = in.read(input, bytesRead, bytesToRead - bytesRead);
                if (result == -1) break; // end of stream
                bytesRead += result;
            }

            for (int i = 0; i < 1024; i++) {
                System.out.print((char) input[i]);
            }

        } catch (FileNotFoundException e) {

        } catch (IOException e){

        }

    }

    public static void generateCharacters(OutputStream out) throws IOException {
        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine = 72;
        int start = firstPrintableCharacter;
        // the +2 is for the carriage return and linefeed

        for (int numLine = 0; numLine < 100; numLine++) {
            for (int i = start; i < start + numberOfPrintableCharacters; i++) {
                out.write((
                        (i - firstPrintableCharacter) % numberOfPrintableCharacters)
                        + firstPrintableCharacter);
            }
            out.write('\r');
            out.write('\r');
            start = ((start + 1) - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
        }
    }

    public static void generateCharactersBuf(OutputStream out) throws IOException {
        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine = 72;
        int start = firstPrintableCharacter;

        byte[] line = new byte[numberOfCharactersPerLine + 2];
        // the +2 is for the carriage return and linefeed

        for (int numLine = 0; numLine < 100; numLine++) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++) {
                line[i - start] = (byte) ((i - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter);
            }
            line[72] = (byte) '\r';
            line[73] = (byte) '\n';
            out.write(line);
            start = ((start + 1) - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
        }
    }
}
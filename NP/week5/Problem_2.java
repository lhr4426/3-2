package homework;

import java.io.*;

public class Problem_2 {
     public static void main(String[] args){

          try{
               OutputStream out = new FileOutputStream("data.txt");
               generateCharacters(out);

          } catch (IOException e) {
               System.err.println(e.getMessage());
          }

          try(InputStream in = new FileInputStream("data.txt")) {
               int bytesRead = 0;
               int bytesToRead = 500;
               byte[] input = new byte[bytesToRead];
               while (bytesRead < bytesToRead) {
                    int result = in.read(input, bytesRead, bytesToRead - bytesRead);
                    if (result == -1) break; // end of stream
                    bytesRead += result;
               }

               for (int i = 0; i < bytesToRead; i++) {
                    System.out.print((char) input[i]);
               }


          } catch (IOException e) {
               System.err.println(e.getMessage());
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
               out.write('\n');
               start = ((start + 1) - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
          }
     }

}

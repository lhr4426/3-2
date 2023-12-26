package practice;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TimeServer {
    public final static int PORT = 3700;
    public static void main(String[] args) {
        // The time protocol sets the epoch at 1900,
        // the Date class at 1970. This number
        // converts between them.
        long differenceBetweenEpochs = 2208988800L;
        try (ServerSocket server = new ServerSocket(PORT)) { while (true) {
            try (Socket connection = server.accept()) { OutputStream out = connection.getOutputStream(); Date now = new Date();
                long msSince1970 = now.getTime();
                long secondsSince1970 = msSince1970/1000;
                long secondsSince1900 = secondsSince1970 + differenceBetweenEpochs;
                byte[] time = new byte[4];
                time[0] = (byte) ((secondsSince1900 & 0x00000000FF000000L) >> 24); // 1 byte로 만들어주기 위해 3 byte만큼 밀기
                time[1] = (byte) ((secondsSince1900 & 0x0000000000FF0000L) >> 16); // 1 byte로 만들어주기 위해 2 byte만큼 밀기
                time[2] = (byte) ((secondsSince1900 & 0x000000000000FF00L) >> 8);  // 1 byte로 만들어주기 위해 1 byte만큼 밀기
                time[3] = (byte) (secondsSince1900 & 0x00000000000000FFL);
                out.write(time); // index = 0 인 데이터부터 바이트가 보내짐
                out.flush();
            } catch (IOException ex) { System.err.println(ex.getMessage());
            } }
        } catch (IOException ex) { System.err.println(ex);
        } }
}

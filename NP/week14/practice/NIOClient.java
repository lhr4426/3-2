package practice;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class NIOClient {
    public static int DEFAULT_PORT = 2222;

    public static void main(String[] args) {

        int port = DEFAULT_PORT;

        try {
            SocketAddress address = new InetSocketAddress("localhost", port);
            SocketChannel client = SocketChannel.open(address);
            ByteBuffer buffer = ByteBuffer.allocate(5); //A-C,\r, \n
            WritableByteChannel out = Channels.newChannel(System.out);
            while (client.read(buffer) != -1) { //ABC
// System.out.println("reading");
                buffer.flip();
                out.write(buffer);
// System.out.print( new String(buffer.array(), "ASCII")) ;
                buffer.clear();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

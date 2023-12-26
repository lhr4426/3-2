package practice;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static int DEFAULT_PORT = 2222; //
    public static void main(String[] args) {
        int port = DEFAULT_PORT;
/*try {
port = Integer.parseInt(args[0]);
} catch (RuntimeException ex) {
port = DEFAULT_PORT;
}
*/
        System.out.println("Listening for connections on port " + port);
        byte[] rotation = new byte[3 * 2];
        for (byte i = 'A'; i <= 'C'; i++) {
            rotation[i - 'A'] = i;
            rotation[i + 3 - 'A'] = i;
        }
        System.out.print(Arrays.toString(rotation)); //ABCABC
/*
for (byte i = '1'; i <= '9'; i++) {
rotation[i -'1'] = i;
rotation[i + 9 - '1'] = i;
}
*/
        ServerSocketChannel serverChannel;
        Selector selector;
        try {
            serverChannel = ServerSocketChannel.open();
            ServerSocket ss = serverChannel.socket();
            InetSocketAddress address = new InetSocketAddress(port);
            ss.bind(address);
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        while (true) {
            try {
                selector.select();
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("/n Accepted connection from " + client);
                        client.configureBlocking(false);
                        SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);
                        ByteBuffer buffer = ByteBuffer.allocate(5);
                        buffer.put(rotation, 0, 3); //ABC
// buffer.put(rotation, 0, 9);
                        buffer.put((byte) '\r');
                        buffer.put((byte) '\n');
                        buffer.flip(); // pos=0, limit=5;
                        key2.attach(buffer);
                    } else if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        if (!buffer.hasRemaining()) {
                            buffer.rewind();
// Get the old first character
                            int first = buffer.get();
                            buffer.rewind();
// Find the new first characters position in rotation
                            int position = first - 'A' + 1;
// copy the data from rotation into the buffer
                            buffer.put(rotation, position, 3); //ABCABC
// Store a line break at the end of the buffer
                            buffer.put((byte) '\r');
                            buffer.put((byte) '\n');
// Prepare the buffer for writing
                            buffer.flip();
                        }
                        client.write(buffer);
                    }
                } catch (IOException ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException cex) {
                    }
                }
            }
        }
    }
}


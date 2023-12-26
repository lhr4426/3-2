package practice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;

public class ChannelBufferEx {

    public static void main(String[] args) throws IOException {

        ByteBuffer buffer1 = ByteBuffer.allocate( 100 );
        // capacity를 100으로 잡는다.

        System.out.println("buffer capacity="+buffer1.capacity());
        System.out.println("buffer limit="+buffer1.limit()); // 처음에는 리미트가 용량과 같다.
        System.out.println("buffer position="+buffer1.position());

        System.out.println();

        buffer1.put((byte)0x41); // A
        buffer1.put((byte)0x42); // B

        // 두 글자를 썼기 때문에 position은 2가 되었을 것.

        System.out.println("buffer capacity="+buffer1.capacity());
        System.out.println("buffer limit="+buffer1.limit());
        System.out.println("buffer position="+buffer1.position());
        System.out.println();

        buffer1.flip();
        // 뒤집었기 때문에 position = 0, limit = 2가 되었을 것.

        System.out.println("buffer capacity="+buffer1.capacity());
        System.out.println("buffer limit="+buffer1.limit());
        System.out.println("buffer position="+buffer1.position());
        System.out.println();

        WritableByteChannel output = Channels.newChannel(System.out);
        // 양방향 채널로 콘솔을 지정한다.

        output.write(buffer1); // 현재 버퍼에 있는 값을 채널에 쓴다. => 콘솔에 "AB" 가 띄워진다.

        System.out.println();

        // 현재 리미트는 여전히 2, 데이터를 썼기 때문에 포지션은 2로 옮겨졌다.
        System.out.println("buffer limit="+buffer1.limit());
        System.out.println("buffer position="+buffer1.position());
        System.out.println();

        buffer1.limit(buffer1.capacity());
        // 리미트를 용량과 동일하게 옮겨준다.

        buffer1.put((byte)0x43); // C
        // 한 글자 더 썼기 때문에 포지션은 3이 되었을 것.

        System.out.println("buffer limit="+buffer1.limit());
        System.out.println("buffer position="+buffer1.position());
        System.out.println();

        buffer1.rewind();
        // rewind는 포지션만 0으로 옮긴다.

        System.out.println("buffer limit="+buffer1.limit());
        System.out.println("buffer position="+buffer1.position());
        System.out.println();

        buffer1.clear();
        // clear는 포지션과 리미트 모두 초기값으로 옮긴다.

        System.out.println("buffer limit="+buffer1.limit());
        System.out.println("buffer position="+buffer1.position());
        System.out.println();

        buffer1.position(1);
        // 포지션을 1로 변경한다.

        buffer1.limit(3);
        // 리미트를 3으로 변경한다.

        System.out.println(buffer1);

        byte[] array = buffer1.array();
        System.out.println(Arrays.toString(array));
        buffer1.compact();

        System.out.println(buffer1);
        array = buffer1.array();
        System.out.println(Arrays.toString(array));



        FileInputStream fin = new FileInputStream("data.txt" );
        FileChannel fc = fin.getChannel();
        ByteBuffer buffer2 = ByteBuffer.allocate( 100 );
        fc.read( buffer2 ); // 읽어서 >> 버퍼에 << 채우는거임 

        byte[] message = new String("test").getBytes();
        FileOutputStream fout = new FileOutputStream( "wrtiedata.txt" );
        FileChannel fcout = fout.getChannel();
        ByteBuffer bufferout = ByteBuffer.allocate( 1024 );
        for (int i=0; i<message.length; ++i) {
            bufferout.put( message[i] ); //
        }

        bufferout.flip();
        fcout.write( bufferout );
    }
}
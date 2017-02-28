/*
 * Created by saurabh on 2/20/2017.
 */

package cs455.scaling.Server;

import cs455.scaling.Threads.ThreadPoolManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.NoSuchAlgorithmException;

import static cs455.scaling.util.sha1.SHA1FromBytes;

public class server {

    private static int Thread_count;
    private static int port;

    public static void main(String [] args) throws IOException, NoSuchAlgorithmException {
        // Accepting the inputs from command line
        port = Integer.parseInt(args[0]);
        Thread_count = Integer.parseInt(args[1]);

        // Creating a server Socket channel using java nio package
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("Server socket created");

        // Creating a ThreadPoolManager object
        ThreadPoolManager manager = new ThreadPoolManager(Thread_count);

        // Listening for the connections
        while (true)
        {
            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead = socketChannel.read(buf);
            System.out.println("Byte count in byte data: " +bytesRead);
            byte [] dst = new byte[bytesRead];
            buf.get(dst);
            String hash = SHA1FromBytes(dst);
            System.out.println("Hash for received String is: " +hash);

            //            while(buf.hasRemaining())
//            {
//                System.out.print((buf.get()));
//            }
            System.out.println("Done Reading");

        }
    }
}

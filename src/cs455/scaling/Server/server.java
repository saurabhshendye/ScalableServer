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
            // Creating a socketchannnel for incoming connections
            // and allocating a buffer of size 8KB
            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer buf = ByteBuffer.allocate(8192);

            // The code henceforth will be a part of worker threads read function
            int bytesRead = socketChannel.read(buf);
            System.out.println("Byte count in byte data: " +bytesRead);
            byte [] dst = buf.array();
            while (buf.hasRemaining())
            {

                System.out.println("Remaining");
                bytesRead = socketChannel.read(buf);
                System.out.println("Byte count in byte data: " +bytesRead);
                dst = buf.array();
                System.out.println("Temp length: " +dst.length);
                System.out.println(buf.remaining());

            }

            String hash = SHA1FromBytes(dst);
            System.out.println("Hash for received String is: " +hash);

            System.out.println("Done Reading");

        }
    }
}

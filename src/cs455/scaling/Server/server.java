/*
 * Created by saurabh on 2/20/2017.
 */

package cs455.scaling.Server;


import cs455.scaling.Threads.Task_Manager;
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

        // Creating a task allocator object
        Task_Manager taskManager = new Task_Manager();

        // Creating a ThreadPoolManager object
        ThreadPoolManager poolManager = new ThreadPoolManager(Thread_count, taskManager);


        // Listening for the connections
        while (true)
        {
            // Creating a socketchannnel for incoming connections
            // and allocating a buffer of size 8KB
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);

            // Register with the selector
            taskManager.getRegistered(socketChannel);

            // The code henceforth will be a part of worker threads read function
            ByteBuffer buf = ByteBuffer.allocate(8192);
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

            System.out.println("Now Writing");

            byte [] b = hash.getBytes();

            ByteBuffer buf_w = ByteBuffer.allocate(b.length);
            System.out.println("Hash length in bytes: " +b.length);
            buf.clear();
            buf_w.put(b);
            buf_w.flip();

            while(buf.hasRemaining())
            {
                socketChannel.write(buf_w);
            }
        }
    }
}

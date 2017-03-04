/*
 * Created by saurabh on 2/20/2017.
 */

package cs455.scaling.Server;


import cs455.scaling.util.SelectorManager;
import cs455.scaling.util.Task_Manager;
import cs455.scaling.Threads.ThreadPoolManager;
import cs455.scaling.util.Tasks;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Set;

//import static cs455.scaling.util.sha1.SHA1FromBytes;


public class server {

//    private static int Thread_count;
//    private static int port;
    private static Selector selector;
//    private static int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;

    public static void main(String [] args) throws IOException, NoSuchAlgorithmException {
        // Opening a selector
        selector = Selector.open();

        // Accepting the inputs from command line
        int port = Integer.parseInt(args[0]);
        int Thread_count = Integer.parseInt(args[1]);

        // Creating a server Socket channel using java nio package
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server socket created and registered ");

        // Creating a selector manager
        SelectorManager selectorManager = new SelectorManager(selector);

        // Creating a task allocator object
        Task_Manager taskManager = new Task_Manager();
//        taskManager.start();

        // Creating a ThreadPoolManager object
        ThreadPoolManager poolManager = new ThreadPoolManager(Thread_count, taskManager);
        poolManager.start();

        // Listening for the connections
        while (true)
        {
            selector.select();

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext())
            {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();
                if (key.isAcceptable())
                {
                    // Accept a new connection and register the socket channel to selector
                    System.out.println("Accepted a new connection");
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);

                    // Removed the current key so as to check on other keys
                    selectedKeys.remove(key);

                    // Put the serversocketchannel key back in queue(Last position)
                    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                }
                else if (key.isReadable())
                {
                    Tasks read = new Tasks(0,(SocketChannel)key.channel());
                    taskManager.Add_task(read);

                    key.cancel();
                }
            }

            // Creating a socketchannnel for incoming connections
//            SocketChannel socketChannel = serverSocketChannel.accept();



            // Register with the selector
//            taskManager.getRegistered(socketChannel);
//            System.out.println("We haven't reached here");


            // The code henceforth will be a part of worker threads read function
//            ByteBuffer buf = ByteBuffer.allocate(8192);
//            int bytesRead = socketChannel.read(buf);
//            System.out.println("Byte count in byte data: " +bytesRead);
//            byte [] dst = buf.array();
//            while (buf.hasRemaining())
//            {
//                System.out.println("Remaining");
//                bytesRead = socketChannel.read(buf);
//                System.out.println("Byte count in byte data: " +bytesRead);
//                dst = buf.array();
//                System.out.println("Temp length: " +dst.length);
//                System.out.println(buf.remaining());
//            }
//
//            String hash = SHA1FromBytes(dst);
//            System.out.println("Hash for received String is: " +hash);
//
//            System.out.println("Done Reading");
//
//            System.out.println("Now Writing");
//
//            byte [] b = hash.getBytes();
//
//            ByteBuffer buf_w = ByteBuffer.allocate(b.length);
//            System.out.println("Hash length in bytes: " +b.length);
//            buf.clear();
//            buf_w.put(b);
//            buf_w.flip();
//
//            while(buf.hasRemaining())
//            {
//                socketChannel.write(buf_w);
//            }
        }
    }

//    private static void getRegistered(SocketChannel channel) throws ClosedChannelException
//    {
//        selector.wakeup();
//        SelectionKey key = channel.register(selector, interestSet);
//        System.out.println("Registered the channel to a selector");
//    }
}

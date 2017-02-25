/*
 * Created by saurabh on 2/20/2017.
 */

package cs455.scaling.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class server {

    private static int Thread_count;
    private static int port;

    public static void main(String [] args) throws IOException
    {
        port = Integer.parseInt(args[0]);
        Thread_count = Integer.parseInt(args[1]);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));

        System.out.println("Server socket created");

        while (true)
        {
            SocketChannel socketChannel = serverSocketChannel.accept();
        }
//        if (serverSocketChannel.is)
    }
}

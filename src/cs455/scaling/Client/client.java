/*
 * Created by saurabh on 2/21/2017.
 */

package cs455.scaling.Client;


import cs455.scaling.WireFormats.payload;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class client {

    private static String server_IP;
    private static int server_port;
    private static int message_rate;

    public static void main(String [] args) throws IOException
    {
        server_IP = args[0];
        server_port = Integer.parseInt(args[1]);
        message_rate = Integer.parseInt(args[2]);

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(server_IP, server_port));

        if(socketChannel.isConnected())
        {
            System.out.println("Socket is connected");
        }

        String newData = "New String to write to file..." + System.currentTimeMillis();

        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());

        buf.flip();

        while(buf.hasRemaining()) {
            socketChannel.write(buf);
        }
        System.out.println("Done Writing");

    }
}

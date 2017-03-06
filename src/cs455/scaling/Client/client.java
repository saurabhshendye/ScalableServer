/*
 * Created by saurabh on 2/21/2017.
 */

package cs455.scaling.Client;


import cs455.scaling.Threads.Client_send_thread;
import cs455.scaling.WireFormats.payload;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;



public class client {

    private static String server_IP;
    private static int server_port;
    private static int message_rate;
    private static LinkedList<String> HashCodeList = new LinkedList<>();

    public static void main(String [] args) throws IOException, NoSuchAlgorithmException {
        server_IP = args[0];
        server_port = Integer.parseInt(args[1]);
        message_rate = Integer.parseInt(args[2]);

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(server_IP, server_port));
        socketChannel.configureBlocking(false);
        if(socketChannel.isConnected())
        {
            System.out.println("Socket is connected");
        }

        Client_send_thread send_T = new Client_send_thread(socketChannel, message_rate);
        send_T.start();


        Selector selector = Selector.open();
        SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ);

        while (true)
        {
            if (key.isReadable())
            {
                ByteBuffer buf = ByteBuffer.allocate(40);
                int bytesRead = socketChannel.read(buf);
                System.out.println("Byte count in byte data: " +bytesRead);
                byte [] dst = buf.array();
                while (buf.hasRemaining())
                {
                    dst = buf.array();
                }
                String hash = new String(dst);
                System.out.println("Received Hash: " +hash);

            }

        }



//        String newData = "New String to write to file..." + System.currentTimeMillis();
//        System.out.println("Sending String: " +newData);
//        byte[] b = newData.getBytes();
//        String hash = SHA1FromBytes(b);
//
//        System.out.println("Hash code for sent message: " +hash);
//
//        ByteBuffer buf = ByteBuffer.allocate(48);
//        buf.clear();
//
////        for (int i = 0; i < 5; i++)
//        {
//            buf.put(newData.getBytes());
//
//            buf.flip();
//
//            while(buf.hasRemaining())
//            {
//                socketChannel.write(buf);
//            }
//
//            buf.clear();
//        }

//        System.out.println("Done Writing");

    }
}

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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


public class client {

    private static String server_IP;
    private static int server_port;
    private static int message_rate;
    private static LinkedList<String> HashCodeList = new LinkedList<>();

    public static void main(String [] args) throws IOException, NoSuchAlgorithmException
    {
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
//            while (key.isValid())
            {
                int i  = selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext())
                {
                    if (i > 0 && key.isReadable())
                    {
                        System.out.println("key is readable now........");
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
                        buf.clear();
                        removeCode(hash);

                        selectedKeys.remove(key);

                        key = socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                }
            }

        }

//        System.out.println("Invalid Key found........");


    }

    public static void addCode(String hash)
    {
        HashCodeList.addLast(hash);
    }

    private static void removeCode(String hash)
    {
        System.out.println("Match found.. Removing from the list");
        HashCodeList.remove(hash);
    }
}

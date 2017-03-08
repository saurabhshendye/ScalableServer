/*
 * Created by saurabh on 2/21/2017.
 */
package cs455.scaling.Threads;


import cs455.scaling.util.SelectorManager;
import cs455.scaling.util.Task_Manager;
import cs455.scaling.util.Tasks;
import cs455.scaling.util.sha1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.security.NoSuchAlgorithmException;

//import static cs455.scaling.util.ThreadPoolManager.getBackInList;
//import static cs455.scaling.util.sha1.SHA1FromBytes;

public class Worker_Thread extends Thread {

    private Task_Manager T_manager;
    private static Tasks current_task;
    private final SelectorManager selectorManager;
    private final ServerStatsPrinter serverStatsPrinter;

    public Worker_Thread(Task_Manager t, String name,
                         SelectorManager selectorManager, ServerStatsPrinter printer)
    {
        super(name);
        this.T_manager = t;
        this.selectorManager = selectorManager;
        this.serverStatsPrinter = printer;
    }

    public void run()
    {
        while (true)
        {
            try
            {
                current_task = T_manager.get_task();

                if (current_task != null)
                {
                    if (current_task.getType() == 0)
                    {
                        SocketChannel channel;
//                        try
                        {
                            channel = current_task.getChannel();
                        }
//                        catch (NullPointerException e)
                        {
//                            System.out.println("Null Pointer Exception in thread: " + this.getName());
//                            continue;
                        }

                        String hash = read_and_hash();
                        Tasks new_task = new Tasks(1,hash,channel);
                        T_manager.Add_task(new_task);
                        selectorManager.getRegistered(channel);
//                    T_manager.getRegistered(channel);
//                        System.out.println("Re-registered by: "+this.getName());
                        serverStatsPrinter.readIncrement();
                    }
                    else if (current_task.getType() == 1)
                    {
                        {
                            write(current_task.getHash_code());
                            serverStatsPrinter.writeIncrement();
                        }
                    }
                }
//                getBackInList(this);
//                System.out.println("Went back to list: " +this.getName());
            }
            catch (InterruptedException|IOException|NoSuchAlgorithmException|NullPointerException e)
            {
                e.getMessage();
            }
        }
    }



    private String read_and_hash() throws IOException, NoSuchAlgorithmException
    {

//        System.out.println("Worker thread read method");

        SocketChannel socketChannel = current_task.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(8192);
        int bytesRead = socketChannel.read(buf);
//        System.out.println("Byte count in byte data: " +bytesRead);
        byte [] dst = buf.array();
        while (buf.hasRemaining())
        {
//            System.out.println("Remaining");
            bytesRead = socketChannel.read(buf);
//            System.out.println("Byte count in byte data: " +bytesRead);
            dst = buf.array();
//            System.out.println("Temp length: " +dst.length);
//            System.out.println(buf.remaining());
        }

        sha1 sha1Hash = new sha1();
//        String hash = sha1Hash.SHA1FromBytes(dst);
//        System.out.println("Hash for received String is: " +hash + " From thread: " +this.getName());

//        System.out.println("Done Reading by: " + this.getName() + "-------" );

//        buf.clear();
        return sha1Hash.SHA1FromBytes(dst);
    }

    private void write(String hash_code) throws IOException
    {
        SocketChannel channel = current_task.getChannel();
        Selector selector = Selector.open();

        SelectionKey key = channel.register(selector, SelectionKey.OP_WRITE);

        boolean CompleteFlag = false;
        while (key.isValid() && !CompleteFlag)
        {
            int i  = selector.select();

            if (i> 0 && key.isWritable())
            {
//                System.out.println("Key is writable.. " +this.getName());
                byte[] hash_bytes = hash_code.getBytes();

                ByteBuffer buf = ByteBuffer.allocate(40);
                buf.clear();

                buf.put(hash_bytes);

                buf.flip();

                int total = 0;
                while (buf.hasRemaining())
                {
                    int write = channel.write(buf);
                    total = total +write;
                }

//                System.out.println("Done Writing: " +this.getName());
                buf.clear();

                CompleteFlag = true;
//                System.out.println("Hash Written: " +hash_code + " by " +this.getName());
            }
        }
        selector.close();
//        System.out.println("Written by: " +this.getName());
    }

}

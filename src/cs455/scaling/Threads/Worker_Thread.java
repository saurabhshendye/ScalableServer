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
import java.nio.channels.SocketChannel;
import java.security.NoSuchAlgorithmException;

//import static cs455.scaling.util.ThreadPoolManager.getBackInList;
//import static cs455.scaling.util.sha1.SHA1FromBytes;

public class Worker_Thread extends Thread {

    private Task_Manager T_manager;
    private static Tasks current_task;
    private final SelectorManager selectorManager;

    public Worker_Thread(Task_Manager t, String name, SelectorManager selectorManager)
    {
        super(name);
        this.T_manager = t;
        this.selectorManager = selectorManager;
    }

    public void run()
    {
        while (true)
        {
            try
            {
                current_task = T_manager.get_task();
//                wait();

                if (current_task != null)
                {
                    if (current_task.getType() == 0)
                    {
                        SocketChannel channel = current_task.getChannel();
                        String hash = read_and_hash();
                        Tasks new_task = new Tasks(1,hash,channel);
                        T_manager.Add_task(new_task);
                        selectorManager.getRegistered(channel);
//                    T_manager.getRegistered(channel);


                    }
                    else if (current_task.getType() == 1)
                    {
                        write(current_task.getHash_code());
                    }
                }


//                getBackInList(this);
//                System.out.println("Went back to list: " +this.getName());
            }
            catch (InterruptedException|IOException|NoSuchAlgorithmException e)
            {
                e.printStackTrace();
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
        String hash = sha1Hash.SHA1FromBytes(dst);
        System.out.println("Hash for received String is: " +hash + " From thread: " +this.getName());

        System.out.println("Done Reading by: " + this.getName() + "-------" );


        return hash;
    }

    private void write(String hash_code)
    {
        System.out.println("Written by: " +this.getName());
    }

    void setDone(Tasks task)
    {
        System.out.println("Set Done");
        current_task = task;
        notify();
    }
}

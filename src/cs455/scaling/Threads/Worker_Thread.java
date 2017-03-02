/*
 * Created by saurabh on 2/21/2017.
 */
package cs455.scaling.Threads;


import cs455.scaling.util.Tasks;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.NoSuchAlgorithmException;

import static cs455.scaling.util.sha1.SHA1FromBytes;

public class Worker_Thread extends Thread {

    private Task_Manager T_manager;
    private static Tasks current_task;

    public Worker_Thread(Task_Manager t)
    {
        this.T_manager = t;
    }

    public synchronized void run()
    {
        while (true)
        {
            try
            {
                wait();
                if (current_task.getType() == 0)
                {
                    String hash = read_and_hash();
                    Tasks new_task = new Tasks(1,hash,current_task.getChannel());
                    T_manager.Add_task(new_task);
                }
                else if (current_task.getType() == 1)
                {
                    write(current_task.getHash_code());
                }
            }
            catch (InterruptedException|IOException|NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }
    }

    synchronized void setDone(Tasks task)
    {
        System.out.println("Set Done");
        current_task = task;
        notify();
    }

    private String read_and_hash() throws IOException, NoSuchAlgorithmException
    {

        System.out.println("Worker thread read method");

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

        String hash = SHA1FromBytes(dst);
        System.out.println("Hash for received String is: " +hash);

        System.out.println("Done Reading");


        return hash;
    }

    private void write(String hash_code)
    {

    }
}

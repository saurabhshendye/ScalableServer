/*
 * Created by saurabh on 2/24/2017.
 */

package cs455.scaling.Threads;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.NoSuchAlgorithmException;

import static cs455.scaling.util.sha1.SHA1FromBytes;

public class Client_send_thread extends Thread
{
    private SocketChannel channel;

    public Client_send_thread(SocketChannel channel)
    {
        this.channel = channel;
    }
    public void run()
    {
        try {

            String newData = "New String to write to file..." + System.currentTimeMillis();
            System.out.println("Sending String: " +newData);
            byte[] b = newData.getBytes();
            String hash = SHA1FromBytes(b);

            System.out.println("Hash code for sent message: " +hash);

            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();



            buf.put(newData.getBytes());

            buf.flip();

            while(buf.hasRemaining())
            {

                channel.write(buf);

            }
            buf.clear();


            System.out.println("Done Writing");
        }
        catch (IOException | NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

    }

}

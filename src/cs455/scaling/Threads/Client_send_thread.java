/*
 * Created by saurabh on 2/24/2017.
 */

package cs455.scaling.Threads;


import cs455.scaling.WireFormats.payload;
import cs455.scaling.util.sha1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.NoSuchAlgorithmException;

//import static cs455.scaling.util.sha1.SHA1FromBytes;

public class Client_send_thread extends Thread
{
    private SocketChannel channel;
    private int rate;

    public Client_send_thread(SocketChannel channel, int rate)
    {
        this.channel = channel;
        this.rate = rate;

    }
    public void run()
    {
        try
        {
//            for (int i = 0; i < 30; i++)
            while (true)
            {
                // Creating a payload and getting corresponding byte Array
                payload p = new payload();
                byte[] b = p.getByteArray();
                System.out.println("Byte array length: " +b.length);

                // Calculating the hash for given byte array
                sha1 sha1Hash = new sha1();
                String hash = sha1Hash.SHA1FromBytes(b);
                System.out.println("Hash code for sent message: " +hash);

                // Creating a Bytebuffer of 8KB size and bringing it to initial position
                ByteBuffer buf = ByteBuffer.allocate(b.length);
                buf.clear();

                // Putting the byte array into the buffer and
                // flipping it to make it write ready
                buf.put(b);
                buf.flip();

                // Writing on the connected socket channel
                while(buf.hasRemaining())
                {
                    channel.write(buf);
                }

                // Clearing the current buffer and making it
                // Write ready for the next iteration
                buf.clear();


                System.out.println("Done Writing");
                Thread.sleep(1000/rate);
            }



        }
        catch (IOException | NoSuchAlgorithmException |InterruptedException e)
        {
            e.printStackTrace();
        }

    }

}

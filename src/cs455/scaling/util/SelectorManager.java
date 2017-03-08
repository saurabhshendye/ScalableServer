/*
 * Created by saurabh on 3/4/2017.
 */
package cs455.scaling.util;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class SelectorManager
{
    private final Selector selector;
    private final Object privateLock = new Object();
    private int waitingRequests = 0;

    public SelectorManager(Selector S)
    {
        this.selector = S;
    }

    public void getRegistered(SocketChannel channel) throws ClosedChannelException
    {
        synchronized (privateLock)
        {
            try
            {
                waitingRequests++;
                privateLock.wait();
            }
            catch (InterruptedException e)
            {
                return;
            }
        }

        try
        {
//            selector.wakeup();
            channel.register(selector, SelectionKey.OP_READ);
            waitingRequests--;
        }
        catch (ClosedChannelException e)
        {
            e.printStackTrace();
        }

//        System.out.println("Getting Re-registered");
    }

    public void clearWaitingRequests()
    {
        while (waitingRequests > 0)
        {
            synchronized (privateLock)
            {
                privateLock.notify();
            }
        }

    }
}

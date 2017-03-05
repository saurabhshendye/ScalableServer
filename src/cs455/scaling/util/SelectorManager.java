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

    public SelectorManager(Selector S)
    {
        this.selector = S;
    }

    public void getRegistered(SocketChannel channel) throws ClosedChannelException
    {

        selector.wakeup();
        channel.register(selector, SelectionKey.OP_READ);
        System.out.println("Getting Re-registered");
    }
}

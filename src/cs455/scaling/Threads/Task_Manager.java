/*
 * Created by saurabh on 2/28/2017.
 */
package cs455.scaling.Threads;

import cs455.scaling.util.Tasks;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class Task_Manager extends Thread
{
    private static LinkedList<Tasks> tasks =new LinkedList<>();
    private Selector selector = Selector.open();
    private int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;

    public Task_Manager() throws IOException
    {

    }

    public void getRegistered(SocketChannel channel) throws ClosedChannelException
    {
        SelectionKey key = channel.register(selector, interestSet);
        System.out.println("Registered the channel to a selector");
    }

    public static void Add_task(Tasks task)
    {
        tasks.addLast(task);
    }

    public static Tasks get_task()
    {
        return tasks.getFirst();
    }

    public static void remove_task(Tasks task)
    {
        tasks.remove(task);
    }

}

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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class Task_Manager extends Thread
{
    private LinkedList<Tasks> tasks = new LinkedList<>();
    private Selector selector = Selector.open();
    private int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;


    public Task_Manager() throws IOException
    {
//        this.selector = S;
    }

    public void run()
    {

        while (true)
        {
            try
            {
//                System.out.println("In the task_managers run method");
//                int i = selector.select();
                selector.select();
//                System.out.println("Ready channels: " +i);

//                if(i == 0)
//                {
//                    continue;
//                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext())
                {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (key.isReadable())
                    {
//                        System.out.println("Channel is read ready..");
//                        System.out.println("Creating a read task");
                        Tasks read = new Tasks(0,(SocketChannel)key.channel());
                        Add_task(read);
                        key.cancel();
                    }

                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    public void getRegistered(SocketChannel channel) throws ClosedChannelException
    {
        selector.wakeup();
        channel.register(selector, interestSet);
        System.out.println("Registered the channel to a selector");

    }


    synchronized void Add_task(Tasks task)
    {
        tasks.addLast(task);
        System.out.println("Added Read Task");
    }

    synchronized Tasks get_task()
    {
        return tasks.peekFirst();
    }

    synchronized void remove_task(Tasks task)
    {
        tasks.remove(task);
    }

}

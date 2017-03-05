/*
 * Created by saurabh on 2/28/2017.
 */
package cs455.scaling.util;

import cs455.scaling.util.Tasks;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class Task_Manager extends Thread
{
    private final Deque<Tasks> tasks = new LinkedList<>();
//    private final Selector selector = Selector.open();
//    private int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;


//    public Task_Manager() throws IOException
//    {
////        this.selector = S;
//    }

//    public void run()
//    {
//
//        while (true)
//        {
//            try
//            {
////                System.out.println("In the task_managers run method");
////                int i = selector.select();
//                selector.select();
////                System.out.println("Ready channels: " +i);
//
////                if(i == 0)
////                {
////                    continue;
////                }
//
//                Set<SelectionKey> selectedKeys = selector.selectedKeys();
//                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
//
//                while (keyIterator.hasNext())
//                {
//                    SelectionKey key = keyIterator.next();
//                    keyIterator.remove();
//
//                    if (key.isReadable())
//                    {
////                        System.out.println("Channel is read ready..");
////                        System.out.println("Creating a read task");
//                        Tasks read = new Tasks(0,(SocketChannel)key.channel());
//                        Add_task(read);
////                        System.out.println("task type: " + read.getType());
//                        key.cancel();
//                    }
//
//                }
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//
//        }
//    }

//    public void getRegistered(SocketChannel channel) throws ClosedChannelException
//    {
//        selector.wakeup();
//        channel.register(selector, interestSet);
//        System.out.println("Registered the channel to a selector");
//
//    }

    public void Add_task(Tasks task)
    {
        synchronized (this){
            tasks.add(task);
            notify();
        }

        System.out.println("Added Read Task");
    }

//    public synchronized Tasks get_task()
    public Tasks get_task() throws InterruptedException {
//        Tasks Temp = tasks.peekFirst();
//        this.Task_count();
//        tasks.remove(Temp);
//        return Temp;

        synchronized (this)
        {
            if (tasks.size() <= 0)
            {
                wait();
            }
        }

        if (tasks.size() <=0)
        {
            return null;
        }
        else
        {
            return tasks.pollFirst();
        }

    }

//    public void remove_task()
//    {
//        tasks.removeFirst();
//    }
//
//    public int Task_count()
//    {
//        return tasks.size();
////        System.out.println("Task Size: " +tasks.size());
//    }

}

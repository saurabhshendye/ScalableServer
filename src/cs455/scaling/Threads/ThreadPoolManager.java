/*
 * Created by saurabh on 2/24/2017.
 */

package cs455.scaling.Threads;


import cs455.scaling.util.Tasks;

import java.util.LinkedList;

public class ThreadPoolManager extends Thread
{
    private LinkedList<Thread> Thread_list = new LinkedList<>();
    private static LinkedList<Tasks> tasks =new LinkedList<>();

    public ThreadPoolManager(int thread_count)
    {
        // Creating the requested number of threads,
        // starting the thread and adding it to Thread list
        for (int i = 0; i < thread_count; i++ )
        {
            Worker_Thread W = new Worker_Thread();
            Thread thread = new Thread(W);
            Thread_list.add(thread);
            thread.start();
        }
        System.out.println("Created the requested number of threads");
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

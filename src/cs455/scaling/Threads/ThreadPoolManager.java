/*
 * Created by saurabh on 2/24/2017.
 */

package cs455.scaling.Threads;


import cs455.scaling.util.Tasks;

import java.util.LinkedList;

public class ThreadPoolManager extends Thread
{
    private Task_Manager manager;
    private LinkedList<Thread> Thread_list = new LinkedList<>();


    public ThreadPoolManager(int thread_count, Task_Manager M)
    {
        // Creating the requested number of threads,
        // starting the thread and adding it to Thread list
        for (int i = 0; i < thread_count; i++ )
        {
            Worker_Thread W = new Worker_Thread(M);
            Thread thread = new Thread(W);
            Thread_list.add(thread);
            thread.start();
        }
        System.out.println("Created the requested number of threads");

        this.manager = M;
    }

    public void run()
    {

    }




}

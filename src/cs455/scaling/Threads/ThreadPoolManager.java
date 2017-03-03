/*
 * Created by saurabh on 2/24/2017.
 */

package cs455.scaling.Threads;


import cs455.scaling.util.Tasks;

import java.util.LinkedList;

public class ThreadPoolManager extends Thread
{
    private Task_Manager manager;
    private static LinkedList<Worker_Thread> Thread_list = new LinkedList<>();


    public ThreadPoolManager(int thread_count, Task_Manager M)
    {
        // Creating the requested number of threads,
        // starting the thread and adding it to Thread list
        for (int i = 0; i < thread_count; i++ )
        {
            Worker_Thread W = new Worker_Thread(M, "Thread-" +i);
//            Thread thread = new Thread(W);
            Thread_list.add(W);
            W.start();
        }
        System.out.println("Created the requested number of threads");

        this.manager = M;
    }

    public void run()
    {
        while (true)
        {
            if (manager.get_task() != null)
            {
                if (Thread_list.peekFirst() == null)
                {
                    continue;
                }
                else
                {
                    Worker_Thread worker = Thread_list.peekFirst();
                    Thread_list.remove(worker);

                    Tasks task = manager.get_task();
                    manager.remove_task(task);

                    worker.setDone(task);
                }
            }
        }
    }

    public static void getBackInList(Worker_Thread worker)
    {
        System.out.println(worker.getName() + " Back to the queue..");
        Thread_list.addLast(worker);
    }
}

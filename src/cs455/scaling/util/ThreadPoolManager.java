/*
 * Created by saurabh on 2/24/2017.
 */

package cs455.scaling.util;


import cs455.scaling.Threads.Worker_Thread;

import java.util.LinkedList;

public class ThreadPoolManager
{
    private final Task_Manager manager;
    private final static LinkedList<Worker_Thread> Thread_list = new LinkedList<>();
    private final SelectorManager selectorManager;


    public ThreadPoolManager(int thread_count, Task_Manager M, SelectorManager selectorManager)
    {
        // Creating the requested number of threads,
        // starting the thread and adding it to Thread list
        for (int i = 0; i < thread_count; i++ )
        {
            Worker_Thread W = new Worker_Thread(M, "Thread-" +i, selectorManager);
//            Thread thread = new Thread(W);
            Thread_list.add(W);

        }
        System.out.println("Created the requested number of threads");

        this.manager = M;
        this.selectorManager = selectorManager;
    }

    public void start()
    {
        for (Worker_Thread worker : Thread_list)
        {
            worker.start();
        }
    }

//    public void run()
//    {
//        while (true)
//        {
////            if (manager.get_task() != null)
//            if (manager.Task_count() != 0)
//            {
//                System.out.println("Task is not null");
//                if (Thread_list.peekFirst() != null)
//                {
//                    Worker_Thread worker = Thread_list.peekFirst();
//                    Thread_list.remove(worker);
//
//                    Tasks task = manager.get_task();
//                    worker.setDone(task);
////                    manager.remove_task();
////                    System.out.println("Task Type: "+task.getType());
//
//                }
//            }
//
//        }
//    }

//    static void getBackInList(Worker_Thread worker)
//    {
//        System.out.println(worker.getName() + " Back to the queue..");
//        Thread_list.addLast(worker);
//    }
}

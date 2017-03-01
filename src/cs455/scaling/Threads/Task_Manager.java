/*
 * Created by saurabh on 2/28/2017.
 */
package cs455.scaling.Threads;

import cs455.scaling.util.Tasks;

import java.util.LinkedList;

public class Task_Manager extends Thread
{
    private static LinkedList<Tasks> tasks =new LinkedList<>();



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

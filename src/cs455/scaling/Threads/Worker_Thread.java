/*
 * Created by saurabh on 2/21/2017.
 */
package cs455.scaling.Threads;


public class Worker_Thread implements Runnable {

    private Task_Manager T_manager;

    public Worker_Thread(Task_Manager t)
    {
        this.T_manager = t;
    }

    public synchronized void run()
    {

    }

    public synchronized void setDone()
    {

    }

    private String read_and_hash()
    {
        String hash = "";

        return hash;
    }

    private void write(String hash_code)
    {

    }
}

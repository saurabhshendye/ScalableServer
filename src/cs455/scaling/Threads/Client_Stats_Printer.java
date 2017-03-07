/*
 * Created by saurabh on 2/26/2017.
 */
package cs455.scaling.Threads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client_Stats_Printer extends Thread
{
    private int send_counter;
    private int receive_counter;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Client_Stats_Printer()
    {
        this.send_counter = 0;
        this.receive_counter = 0;
    }

    public void run()
    {
        while (true)
        {
            long start = System.currentTimeMillis();
            while (start - System.currentTimeMillis() < 10)
            {

            }

            printStats();
            resetCounters();
        }


    }

    public void incrementSendCount()
    {
        send_counter++;
    }

    public void incrementRecCount()
    {
        receive_counter++;
    }

    private void resetCounters()
    {
        send_counter = 0;
        receive_counter = 0;
    }

    private void printStats()
    {
        Date date = new Date();
        System.out.println(dateFormat.format(date) + " Total Sent Count: " + send_counter
                            + ", " + "Total Received Count: " +receive_counter);
    }


}

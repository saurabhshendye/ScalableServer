/*
 * Created by saurabh on 3/7/2017.
 */

package cs455.scaling.Threads;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ServerStatsPrinter extends Thread
{
    private int readProcessed;
    private int writeProcessed;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private int numberOfConnections;

    public ServerStatsPrinter()
    {
        this.readProcessed = 0;
        this.writeProcessed = 0;
        this.numberOfConnections = 0;
    }

    public void run()
    {
        System.out.println("Stats Printer Run method");
        while (true)
        {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            printStats();
            resetCounters();
        }
    }

    void readIncrement()
    {
        readProcessed++;
    }

    void writeIncrement()
    {
        writeProcessed++;
    }

    private void resetCounters()
    {
        readProcessed = 0;
        writeProcessed = 0;
    }

    public void addConnection()
    {
        numberOfConnections++;
    }

    public void removeConnection()
    {
        numberOfConnections--;
    }

    private void printStats()
    {
        Date date = new Date();
        int average = readProcessed + writeProcessed;
        average = average/2;
        int rate = average/5;
        System.out.println(dateFormat.format(date) + " Current Server Throughput: "
                            + rate + " messages/sec" + " Number of active Connections: "
                            + numberOfConnections);
    }


}

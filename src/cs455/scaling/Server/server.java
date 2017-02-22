/*
 * Created by saurabh on 2/20/2017.
 */

package cs455.scaling.Server;

public class server {

    private static int Thread_count;
    private static int port;

    public static void main(String [] args)
    {
        port = Integer.parseInt(args[0]);
        Thread_count = Integer.parseInt(args[1]);
    }
}

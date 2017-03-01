/*
 * Created by saurabh on 2/26/2017.
 */
package cs455.scaling.util;


import java.nio.channels.SocketChannel;

public class Tasks
{
    private int type;
    private String hash_code;
    private SocketChannel channel;

    // Write Task
    // Type should be equal to 1
    public Tasks(int type, String hash, SocketChannel C)
    {
        this.hash_code = hash;
        this.type = type;
        this.channel = C;
    }

    // Read Task
    // Type should be equal to 0
    public Tasks(int type, SocketChannel C)
    {
        this.type = type;
        this.channel = C;
    }

    public int getType()
    {
        return type;
    }

    public String getHash_code()
    {
        return hash_code;
    }

    public SocketChannel getChannel()
    {
        return channel;
    }

}

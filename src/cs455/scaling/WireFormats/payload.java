/*
 * Created by saurabh on 2/23/2017.
 */

package cs455.scaling.WireFormats;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class payload
{
    private long [] long_array = new long[1024];
//    private int [] int_array = new int[2048];

    public payload()
    {
        Random generate = new Random();
        for (int i = 0; i < long_array.length; i++)
        {
            long_array[i] = generate.nextInt();

        }
    }

    public byte[] getByteArray () throws IOException
    {
        ByteArrayOutputStream baopstream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baopstream));

//        int Len = 8192;
//        dout.writeInt(Len);

        for (int i = 0; i < long_array.length; i++)
//        for (long num : long_array)
        {
//            dout.writeLong(num);
            dout.writeLong(long_array[i]);
//            System.out.println(long_array[i]);
        }

        dout.flush();
        byte[] marshaled = baopstream.toByteArray();

        System.out.println("marshaled byte array length: "+marshaled.length);

        baopstream.close();
        dout.close();

        return marshaled;
    }
}

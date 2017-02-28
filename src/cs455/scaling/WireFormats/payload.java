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
    private long [] long_array = new long[128];

    public payload()
    {
        Random generate = new Random();
        for (int i = 0; i < long_array.length; i++)
        {
            long_array[i] = generate.nextLong();
        }
    }

    public byte[] getByteArray () throws IOException
    {
        ByteArrayOutputStream baopstream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baopstream));

//        int Len = 8192;
//        dout.writeInt(Len);

//        for (int i = 0; i < long_array.length; i++)
        for (long num : long_array)
        {
            dout.writeLong(num);
        }

        byte[] marshaled = baopstream.toByteArray();

        baopstream.close();
        dout.close();

        return marshaled;
    }
}

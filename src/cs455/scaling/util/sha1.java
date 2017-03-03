/*
 * Created by saurabh on 2/23/2017.
 */
package cs455.scaling.util;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha1 {

    public sha1()
    {

    }
    public String SHA1FromBytes(byte [] data) throws NoSuchAlgorithmException
    {
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        byte [] hash = digest.digest(data);
        BigInteger hashInt = new BigInteger(1, hash);


        return hashInt.toString(16);
    }

}

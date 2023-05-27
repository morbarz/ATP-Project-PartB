

package IO;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;

public class SimpleDecompressorInputStream extends InputStream
{
    public InputStream in;
    public ArrayList<Integer> array = new ArrayList<>();
    public byte[] tempArr = new byte[2];
    public byte[] ByteArray;
    public int size = 0;

    public SimpleDecompressorInputStream(InputStream inputStream)
    {
        this.in = inputStream;
    }
    @Override
    public int read() throws IOException {return 0;}

    public int read(byte[] bytes) throws IOException
    {
        // init
        ByteArray = new byte[bytes.length];
        byte[] readBytes = in.readAllBytes();
        boolean flag = false;
        int zero = 0, one = 0;

        // convert directly the size+start+end into our result.
        for (int i = 0; i < 12; i++)
        {
            ByteArray[size++] = readBytes[i];
        }

        // getting all bytes[2] in tuples of 2 converting into ints.
        if (readBytes[12] == 1)
        {
            flag = true;
        }
        byte[] t = new byte[2];
        t[0] = bytes[4];
        t[1] = bytes[5];
        int checkSize = BytesTooInt(t);
        if (checkSize <= 500)
            readBytes[12] = 0;

        for (int b = 12; b < readBytes.length; b+=2)
        {
            if (b == readBytes.length - 1)
            {
                tempArr[0] = 0;
                tempArr[1] = readBytes[b];
                int val = BytesTooInt(tempArr);
                if (!flag)
                {
                    for (int i = 0; i < val; i++)
                    {
                        ByteArray[size++] = (byte)0;
                    }
                }
                if (flag)
                {
                    for (int i = 0; i < val; i++)
                    {
                        ByteArray[size++] = (byte)1;
                    }
                }
                flag = !flag;
                break;
            }
            else{
                tempArr[0] = readBytes[b];
                tempArr[1] = readBytes[b+1];
            }

            int val = BytesTooInt(tempArr);
            if (!flag)
            {
                for (int i = 0; i < val; i++)
                {
                    if (size == bytes.length)
                        continue;
                    ByteArray[size++] = (byte)0;
                }
            }
            if (flag)
            {
                for (int i = 0; i < val; i++)
                {
                    if (size == bytes.length)
                        continue;
                    ByteArray[size++] = (byte)1;
                }
            }
            flag = !flag;
        }
        for (int i = 0; i < ByteArray.length; i++)
        {
            bytes[i] = ByteArray[i];
        }

        ///TODO gets an array of numbers and need to break to atoms. [0,7,10,16] -> [0000000,1111111111, 00....]
        return 0;
    }

    // convert int into byte[2].
    public byte[] IntTooBytes(int value)
    {
        byte[] bytes = new byte[2];
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            bytes[length - i - 1] = (byte) (value & 0xFF);
            value >>= 8;
        }
        return bytes;
    }

    //converts bytes into int.
    public int BytesTooInt(byte[] bytes)
    {
        int v = new BigInteger(bytes).intValue();
        return v;
    }

}

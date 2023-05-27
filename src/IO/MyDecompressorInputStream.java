package IO;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;

public class MyDecompressorInputStream extends InputStream {
    public InputStream in;
    public byte[] readBytes;

    public MyDecompressorInputStream(InputStream inputStream)
    {
        this.in = inputStream;
    }
    @Override
    public int read() throws IOException {
        return 0;
    }

    public int read(byte[] bytes) throws IOException {
        decompress(bytes);
        return 0;
    }
    public void decompress(byte[] bytes ) throws IOException {        readBytes = new byte[bytes.length];
        readBytes = in.readAllBytes();
        int count = 12;
        // converting the start/end/size of the maze.
        for (int i = 0; i < 12; i++)
        {
            bytes[i] = readBytes[i];
        }

        // convert all bytes into 8's of binary numbers.

        for (int i = 12; i < readBytes.length; i+=2)
        {
            if (i == readBytes.length - 2)
            {
                int fixed_size = ((bytes.length - 12) % 8);
                byte[] temp = new byte[2];
                temp[0] = readBytes[i];
                temp[1] = readBytes[i + 1];
                int val = BytesTooInt(temp);
                int[] bin = IntToBinary(val);
                if (fixed_size != 0)
                {
                    for (int j = 0; j < fixed_size; j++)
                    {
                        bytes[count++] = (byte) bin[j];
                    }
                }
                else
                {
                    for (int j = 0; j < 8; j++) {
                        bytes[count++] = (byte) bin[j];
                    }
                }
                continue;
            }
            if (i == readBytes.length - 1)
            {
                byte[] temp = new byte[2];
                temp[1] = readBytes[readBytes.length - 1];
                int val = BytesTooInt(temp);
                int[] bin = IntToBinary(val);
                for (int j = 0; j < 8; j++)
                {
                    if (count == bytes.length - 1)
                        break;
                    bytes[count++] = (byte)bin[j];
                }
                break;
            }
            byte[] temp = new byte[2];
            temp[0] = readBytes[i];
            temp[1] = readBytes[i + 1];
            int val = BytesTooInt(temp);
            int[] bin = IntToBinary(val);
            for (int j = 0; j < 8; j++)
            {
                if (count == bytes.length - 1)
                    break;
                bytes[count++] = (byte)bin[j];
            }
        }
        /// TODO convert all bytes into 1's & 0's.
    }


    public int[] IntToBinary(int val)
    {
        String str = Integer.toBinaryString(val);
        int[] arr = new int[8];
        int index = 8 - str.length();
        char[] cArr = str.toCharArray();
        for (int i = 0; i < cArr.length; i++)
        {
            int x = Character.getNumericValue(cArr[i]);
            arr[index++] = x;
        }
        return arr;
    }

    //converts bytes into int.
    public int BytesTooInt(byte[] bytes)
    {
        int v = new BigInteger(bytes).intValue();
        return v;
    }

}

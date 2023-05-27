
package IO;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;

public class SimpleCompressorOutputStream extends OutputStream
{
    public OutputStream out;
    public ArrayList<Integer> array = new ArrayList<>();

    public byte[] ByteArray;
    public int size = 0;
    public int counter = 1;

    public SimpleCompressorOutputStream(OutputStream outputStream)
    {
        this.out = outputStream;
    }

    public void write(int b) throws IOException {}


    public void write(byte[] bytes) throws IOException {

        ByteArray = new byte[bytes.length * 2];
        boolean flag = false;
        int zero_count = 0, one_count = 0;
        // Convert the size, start, end
        for (int i = 0; i < 12; i++)
        {
            ByteArray[size++] = bytes[i];
        }

        // count sequences of 0,1
        if (bytes[12] == 1)
            flag = true;

        for (int b = 12; b < ByteArray.length; b++) {
            if (b == bytes.length - 1)
            {
                if (ByteArray[b] == 0)
                {
                    if (ByteArray[b-1] != 0)
                    {
                        counter++;
                        this.array.add(one_count);
                        this.array.add(0);
                    }
                    else
                    {
                        zero_count++;
                        this.array.add(zero_count);
                    }
                }
                else    //ByteArray[b] == 1
                {
                    if (ByteArray[b-1] != 1)
                    {
                        counter++;
                        this.array.add(zero_count);
                        this.array.add(1);
                    }
                    else
                    {
                        one_count++;
                        this.array.add(one_count);
                    }
                }
                break;
            }
            int curr = (int)bytes[b];
            int next = (int)bytes[b + 1];

            if (!flag) {
                zero_count++;
            } else {
                one_count++;
            }
            if (curr != next)
            {
                counter++;
                if (!flag)
                {
                    this.array.add(zero_count);
                    zero_count = 0;
                }
                if (flag)
                {
                    this.array.add(one_count);
                    one_count = 0;
                }
                flag = !flag;
            }
        }

        // convert all ints in the ListArray to bytes[2] + adding the byte[2] into our result.

        for (int i = 0; i < array.size(); i++)
        {
            int val = array.get(i);

            byte[] temp = new byte[2];
            temp = IntTooBytes(val);
            ByteArray[size++] = temp[0];
            ByteArray[size++] = temp[1];
        }
        if (bytes[12] == 1)
            ByteArray[12] = 1;
        else
            ByteArray[12] = 0;

        byte[] resultArr = new byte[counter*2 + 12];
        for (int i = 0; i < resultArr.length; i++)
        {
            resultArr[i] = ByteArray[i];
        }
        out.write(resultArr);
    }
    // gets a byte array need to check each byte if comes in blocks and count it.



    public byte[] IntTooBytes(int value) {
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
        int temp = new BigInteger(bytes).intValue();
        return temp;
    }

}

package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MyCompressorOutputStream extends OutputStream
{
    public OutputStream out;
    public ArrayList<Integer> array = new ArrayList<>();
    public byte[] ByteArray;
    public int size = 0;

    public MyCompressorOutputStream(OutputStream outputStream)
    {
        this.out = outputStream;
    }

    public void write(int b) throws IOException
    {
        out.write((byte) b );
    }

    public byte[] compress(byte[] b) throws IOException {
        {
            // inserting start/end/size of maze and initialize byte array.
            if ((b.length - 12) % 8 == 0)
                ByteArray = new byte[(int) Math.ceil((double) (b.length - 12) / 4) + 12];
            else {
                ByteArray = new byte[(b.length - 12) / 4 + 14];
            }
            for (int i = 0; i < 12; i++) {
                ByteArray[i] = b[i];
                size++;
            }
            int count = 0;
            char[] binary = new char[8];
            for (int i = 12; i < b.length; i++) {
                if ((int) b[i] == 0)
                    binary[count++] = '0';
                else
                    binary[count++] = '1';

                if (count == 8) {
                    int val = BinaryToInt(binary);

                    if (val < 255) {  // Check if the value is less than 255 before compressing
                        ByteArray[size] = (byte) val;
                        size++;
                    } else {
                        while (val > 0) {
                            int remainder = val % 256;
                            ByteArray[size] = (byte) remainder;
                            size++;
                            val = val / 256;
                        }
                    }

                    count = 0;
                } else if (i == b.length - 1) {
                    int add = 8 - ((b.length - 12) % 8);
                    for (int j = 0; j < add; j++) {
                        binary[count++] = '0';
                    }
                    int val = BinaryToInt(binary);

                    if (val < 255) {  // Check if the value is less than 255 before compressing
                        ByteArray[size] = (byte) val;
                        size++;
                    } else {
                        while (val > 0) {
                            int remainder = val % 256;
                            ByteArray[size] = (byte) remainder;
                            size++;
                            val = val / 256;
                        }
                    }
                }
            }
            out.write(ByteArray);
        }
        return ByteArray;
    }

    // convert binary into int.
    public int BinaryToInt(char[] bin) /// [0,0,0,0,1,0,0,1,0,0,0,1,1,1,1,0] = 2334
    {
        int level = 0;
        int result = 0;
        for (int b = bin.length - 1; b >= 0; b--) {
            if (bin[b] == '1') {
                result += Math.pow(2, level);
            }
            level++;
        }
        return result;
    }

    // convert int into byte array.
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
}

//package IO;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigInteger;
//import java.util.ArrayList;
//
//public class MyDecompressorInputStream extends InputStream {
//    public InputStream in;
//    public byte[] readBytes;
//
//    public MyDecompressorInputStream(InputStream inputStream)
//    {
//        this.in = inputStream;
//    }
//
//    @Override
//    public int read() throws IOException {
//        return 0;
//    }
//
//    public int read(byte[] bytes) throws IOException
//    {
//        readBytes = new byte[bytes.length];
//        readBytes = in.readAllBytes();
//        int count = 12;
//        // converting the start/end/size of the maze.
//        for (int i = 0; i < 12; i++)
//        {
//            bytes[i] = readBytes[i];
//        }
//
//        // convert all bytes into 8's of binary numbers.
//
//        for (int i = 12; i < readBytes.length; i+=2)
//        {
//            if (i == readBytes.length - 2)
//            {
//                int fixed_size = ((bytes.length - 12) % 8);
//                byte[] temp = new byte[2];
//                temp[0] = readBytes[i];
//                temp[1] = readBytes[i + 1];
//                int val = BytesTooInt(temp);
//                int[] bin = IntToBinary(val);
//                if (fixed_size != 0)
//                {
//                    for (int j = 0; j < fixed_size; j++)
//                    {
//                        bytes[count++] = (byte) bin[j];
//                    }
//                }
//                else
//                {
//                    for (int j = 0; j < 8; j++) {
//                        bytes[count++] = (byte) bin[j];
//                    }
//                }
//                continue;
//            }
//            if (i == readBytes.length - 1)
//            {
//                byte[] temp = new byte[2];
//                temp[1] = readBytes[readBytes.length - 1];
//                int val = BytesTooInt(temp);
//                int[] bin = IntToBinary(val);
//                for (int j = 0; j < 8; j++)
//                {
//                    if (count == bytes.length - 1)
//                        break;
//                    bytes[count++] = (byte)bin[j];
//                }
//                break;
//            }
//            byte[] temp = new byte[2];
//            temp[0] = readBytes[i];
//            temp[1] = readBytes[i + 1];
//            int val = BytesTooInt(temp);
//            int[] bin = IntToBinary(val);
//            for (int j = 0; j < 8; j++)
//            {
//                if (count == bytes.length - 1)
//                    break;
//                bytes[count++] = (byte)bin[j];
//            }
//        }
//        /// TODO convert all bytes into 1's & 0's.
//        return 0;
//    }
//
//    // converts from int into a list of ints(binary values) 100 -> [1,1,0,0,1,0,0]
//    public int[] IntToBinary(int val)
//    {
//        String str = Integer.toBinaryString(val);
//        int[] arr = new int[8];
//        int index = 8 - str.length();
//        char[] cArr = str.toCharArray();
//        for (int i = 0; i < cArr.length; i++)
//        {
//            int x = Character.getNumericValue(cArr[i]);
//            arr[index++] = x;
//        }
//        return arr;
//    }
//
//    //converts bytes into int.
//    public int BytesTooInt(byte[] bytes)
//    {
//        int v = new BigInteger(bytes).intValue();
//        return v;
//    }
//
//}
package IO;

import javax.lang.model.element.NestingKind;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class MyDecompressorInputStream extends InputStream {
    InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    /**
     * @param b compressed byte array of a maze
     * @return
     * @throws IOException
     * decompressing a byte array
     */
    @Override
    public int read(byte[] b) throws IOException{
        byte[] list = in.readAllBytes();
        int index=0;
        while (list[index] != -2) {
            b[index] = list[index];
            index++;
        }
        b[index] = list[index];
        index++;
        int iterator = index;
        String binaryNum;
        while (index < list.length){
            binaryNum = String.format("%" + 7 + "s", Integer.toBinaryString(list[index])).replaceAll(" ", "0");
            StringBuilder rev = new StringBuilder();
            rev.append(binaryNum);
            rev.reverse();
            binaryNum = rev.toString();
            for (int i=0 ; i < binaryNum.length() ; i++){
                if (iterator >= b.length) break;
                b[iterator] = (byte)(binaryNum.charAt(i) - '0');
                iterator++;
            }
            index ++;
        }
        return 0;
    }
}
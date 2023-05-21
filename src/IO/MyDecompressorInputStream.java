package IO;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;

public class MyDecompressorInputStream extends InputStream {
    private InputStream in;


    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {

        return 0;
    }


    public int read(byte[] b) throws IOException{
        ArrayList<Byte> finalarr = new ArrayList<>();
        int index = 0 ; //follow finalarr
        for (int i = 0 ; i<12 ;i++){
            finalarr.add(b[i]);
        }
        for (int j = 12 ; j<b.length ; j=j+2 ){
            byte[] loopByte = new byte[2];
            loopByte[0]=b[j];
            loopByte[1]=b[j+1];
            int numfrombytes = BytesTooInt(loopByte);
            int[] loopchar = new int[8];
            loopchar=IntToBinary(numfrombytes);
            for (int p=0 ; p < loopchar.length ; p++ ){
                if (loopchar[p]=='0'){
                    finalarr.add((byte) 0);
                }
                else if (loopchar[p]== '1'){
                    finalarr.add((byte) 1);
                }
            }
            byte[] byteArray = new byte[finalarr.size()];
            for (int i = 0; i < finalarr.size(); i++) {
                byteArray[i] = finalarr.get(i);
            }
            read(byteArray);
        }
        return 0;
    }

    // converts from int into a list of ints(binary values) 100 -> [1,1,0,0,1,0,0]
    public int[] IntToBinary(int val) {
        String str = Integer.toBinaryString(val);
        int[] arr = new int[8];
        int index = 8 - str.length();
        char[] cArr = str.toCharArray();
        for (int i = 0; i < cArr.length; i++) {
            int x = Character.getNumericValue(cArr[i]);
            arr[index++] = x;
        }
        return arr;
    }

    //converts bytes into int.
    public int BytesTooInt(byte[] bytes) {
        int v = new BigInteger(bytes).intValue();
        return v;
    }
}

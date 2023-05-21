package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;
    private ArrayList<Byte> newByteArray = new ArrayList<>();

    public SimpleCompressorOutputStream(OutputStream out){
        this.out=out;
    }
    @Override
    public void write(int b) throws IOException {


    }    @Override
    public void write(byte[] b) throws IOException {
        int counter = 0 ;
        for (int i = 0 ; i<12 ; i++) { //0-3 for maze size , 4-7 for start position , 8-11 for goal position .
            counter++;
            newByteArray.add(b[i]);
        }
        //from 7st place in array it holds maze data , this data
        // representing each number(0/1) in a raw
        //for example - if we have 0,0,0,0,0,1,1,0,0,0,0 -in final_arr it looks like 5,2,4
        byte zero = 0;
        byte one = 0;
        int index = 12;
        byte curr = b[index];
        if (curr == 1) {
            newByteArray.add((byte) 0);
            counter++;
            index++;
        }
        while (index<b.length ) {
            if (curr == 0) {
                if (one != 0) {
                    newByteArray.add( one);
                    counter++;
                    index++;
                    one = 0;
                }
                zero++;
                curr = b[index];
                continue;
            }
            if (curr == 1) {
                if (zero != 0) {
                    newByteArray.add( one);
                    counter++;
                    index++;
                    zero = 0;
                }
            }
            one++;
            curr = b[index];
        }
        byte[] compressedArray = new byte[newByteArray.size()];
        for (int i = 0; i < newByteArray.size(); i++) {
            compressedArray[i] = newByteArray.get(i);
        }

        out.write(compressedArray);
    }
}



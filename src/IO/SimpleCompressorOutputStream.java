package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;
    public SimpleCompressorOutputStream(OutputStream out){
        this.out=out;
    }
    @Override
    public void write(int b) throws IOException {


    }    @Override
    public void write(byte[] b) throws IOException {
        byte[] start_with_zero_arr = new byte[b.length];
        int counter = 0 ;
        for (int i = 0 ; i<12 ; i++) { //0-3 for maze size , 4-7 for start position , 8-11 for goal position .
            start_with_zero_arr[i] = b[i];
            counter++;
        }
        //from 7st place in array it holds maze data , this data
        // representing each number(0/1) in a raw
        //for example - if we have 0,0,0,0,0,1,1,0,0,0,0 -in final_arr it looks like 5,2,4
        byte zero = 0;
        byte one = 0;
        int index = 12;
        byte curr = b[index];
        if (curr == 1) {
            start_with_zero_arr[counter] = 0;
            counter++;
            index++;
        }
        while (index<b.length ) {
            if (curr == 0) {
                if (one != 0) {
                    start_with_zero_arr[counter]=one;
                    counter++;
                    index++;
                    //curr = b[index];
                    one = 0;
                }
                zero++;
                curr = b[index];
                continue;
            }
            if (curr == 1) {
                if (zero != 0) {
                    start_with_zero_arr[counter]=zero;
                    counter++;
                    index++;
                    //curr = b[index];
                    zero = 0;
                }
            }
            one++;
            curr = b[index];
        }
        write(start_with_zero_arr);
    }    }



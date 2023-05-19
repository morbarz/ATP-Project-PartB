package IO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;
    public MyCompressorOutputStream(OutputStream out ){
        this.out=out;
    }
    @Override
    public void write(int b) throws IOException {

    }
    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
        return b;
    }
}

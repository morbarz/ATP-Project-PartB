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

    }
}

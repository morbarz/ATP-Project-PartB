package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;
    private ArrayList<Byte> originalByteArray = new ArrayList<>();
    private int currentIndex = 0;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        if (currentIndex < originalByteArray.size()) {
            return originalByteArray.get(currentIndex++);
        }
        return -1; // End of stream
    }

    public void read(Byte[] b) throws IOException {
        for (int i = 0; i < 12; i++) { // Read the metadata (start position, goal position, size)
            originalByteArray.add(b[i]);
        }

        for (int j = 12; j < b.length; j++) {
            if (j % 2 == 0) { // Even position in the array
                for (int k = 0; k < b[j]; k++) {
                    originalByteArray.add((byte) 0);
                }
            } else { // Odd position in the array
                for (int k = 0; k < b[j]; k++) {
                    originalByteArray.add((byte) 1);
                }
            }
        }
    }
}

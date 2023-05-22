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


    public byte[] decompress(byte[] compressedData) throws IOException {
        // Extract start/end/size of maze from compressed data
        byte[] originalData = new byte[12];
        for (int i = 0; i < 12; i++) {
            originalData[i] = compressedData[i];
        }

        int uncompressedSize = (compressedData.length - 12) * 8;
        byte[] decompressedData = new byte[uncompressedSize + 12];
        for (int i = 0; i < 12; i++) {
            decompressedData[i] = originalData[i];
        }

        int index = 12;
        int count = 0;
        char[] binary = new char[8];
        for (int i = 12; i < compressedData.length; i++) {
            int val = compressedData[i] & 0xFF;
            if (val < 255) {  // Check if the value is less than 255 (uncompressed)
                String binaryString = Integer.toBinaryString(val);
                int leadingZeros = 8 - binaryString.length();
                for (int j = 0; j < leadingZeros; j++) {
                    binary[count++] = '0';
                }
                for (int j = 0; j < binaryString.length(); j++) {
                    binary[count++] = binaryString.charAt(j);
                }
            } else { // Compressed value
                while (i < compressedData.length && val == 255) {
                    i++;
                    val = compressedData[i] & 0xFF;
                }
                i--;
                binary = IntToBinary(val); // Convert compressed value to binary
                count = 0;
            }

            if (count == 8) {
                byte[] byteArr = BinaryToByteArray(binary);
                for (byte b : byteArr) {
                    decompressedData[index++] = b;
                }
                count = 0;
            }
        }

        return decompressedData;
    }

    // Convert binary to byte array
    public byte[] BinaryToByteArray(char[] binary) {
        byte[] byteArr = new byte[binary.length / 8];
        for (int i = 0; i < binary.length; i += 8) {
            String binaryString = new String(binary, i, 8);
            int val = Integer.parseInt(binaryString, 2);
            byteArr[i / 8] = (byte) val;
        }
        return byteArr;
    }

    // Convert int to binary
    public char[] IntToBinary(int val) {
        char[] binary = new char[8];
        for (int i = 7; i >= 0; i--) {
            binary[i] = (char) ('0' + (val & 1));
            val >>= 1;
        }
        return binary;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    public int read(byte[] b) throws IOException {
        // Read from the underlying input stream and fill the byte array 'b' with decompressed data.
        // Return the number of bytes read.

        int bytesRead = in.read(b, 0, b.length);

        if (bytesRead > 0) {
            decompress(b); // Decompress the data read from the input stream
        }

        return bytesRead;
    }
}

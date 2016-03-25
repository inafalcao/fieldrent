package br.com.fieldrent.util;

/**
 * Created by inafalcao on 3/18/16.
 */
public class ByteUtil {

    public static byte[] boxedToPrimitiveArray(Byte[] bytes) {
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[i];
        }
        return result;
    }

    public static Byte[] primitiveToBoxedArray(byte[] bytes) {
        Byte[] result = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = Byte.valueOf(bytes[i]);
        }
        return result;
    }

}

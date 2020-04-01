/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.Arrays;
import java.util.List;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Administrator
 */
public class BytesUtils {

    public static Byte[] getSubArray(Byte[] source, int offset, int len) {
        Byte[] result = new Byte[len];
        int cx = 0;
        for (int i = offset; i < source.length; i++) {
            result[cx] = source[i];
            cx++;
            if (cx == len) {
                break;
            }
        }
        return result;
    }

    public static List<Byte> getSubArrayAsList(Byte[] source, int offset, int len) {
        return Arrays.asList(getSubArray(source, offset, len));
    }

    public static List<Byte> toList(Byte[] source) {
        return getSubArrayAsList(source, 0, source.length);
    }

    public static int sum(Byte[] source, int offset, int count) {
        int sum = 0;
        int cx = offset;
        for (int i = 0; i < count; i++) {
            sum += source[cx];
            cx++;
        }
        return sum;
    }

    public static byte[] tobytes(Byte[] bytes) {
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[i];
        }
        return result;
    }

    public static Byte[] toBytes(byte[] bytes) {
        Byte[] result = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[i];
        }
        return result;
    }

    public static String toHexString(byte[] bArr,String split) {
        if (bArr == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(bArr.length);
        String sTmp;

        for (int i = 0; i < bArr.length; i++) {
            sTmp = Integer.toHexString(0xFF & bArr[i]).toUpperCase();
            if (sTmp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTmp);
            if(i!=bArr.length-1)
            {
                sb.append(split);
            }
        }

        return sb.toString();
    }
}

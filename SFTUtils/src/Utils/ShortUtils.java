/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ShortUtils {

    /**
     * 获取高字节数据
     *
     * @param source
     * @return
     */
    public static byte HByte(short source) {
        return (byte) ((source >> 8) & 0xFF);
    }

    /**
     * 获取低字节数据
     *
     * @param source
     * @return
     */
    public static byte LByte(short source) {
        return (byte) (source & 0xFF);
    }

    public static List<Byte> getBytesAsList(short source, boolean isHighFirst) {
        ArrayList<Byte> value = new ArrayList<Byte>();
        int btLen = 2;
        int maxLen = 16;

        ArrayList<Byte> bytes = new ArrayList<Byte>();
        if (isHighFirst) {
            for (int i = 0; i < btLen; i++) {
                bytes.add((byte) ((source >> (maxLen - ((i + 1) << 3))) & 0xff));
            }
        } else {
            for (int i = 0; i < 2; i++) {
                bytes.add((byte) ((source >> (i << 3)) & 0xff));
            }
        }
        return bytes;
    }

    public static Byte[] getBytes(short source, boolean isHighFirst) {
        return (Byte[]) getBytesAsList(source, isHighFirst).toArray(new Byte[0]);
    }
}

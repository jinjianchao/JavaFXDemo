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
public class IntUtils {

    public static List<Byte> getBytesAsList(int data, boolean isHighFirst) {
        int btLen = 4;
        int maxLen = 32;
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        if (isHighFirst) {
            for (int i = 0; i < btLen; i++) {
                bytes.add((byte) ((data >> (maxLen - ((i + 1) << 3))) & 0xff));
            }
        } else {
            for (int i = 0; i < btLen; i++) {
                bytes.add((byte) ((data >> (i << 3)) & 0xff));
            }
        }
        return bytes;
    }

    public static Byte[] getBytes(int data, boolean isHighFirst) {
        return (Byte[]) getBytesAsList(data, isHighFirst).toArray(new Byte[0]);
    }

}

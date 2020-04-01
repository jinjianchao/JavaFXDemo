/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base;

import Enums.CommType;
import Utils.BytesUtils;
import Utils.IntUtils;
import Utils.ShortUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class TXXBaseController {
    private int sendWait;
    
    Map<Integer, UDPPackage> items;

    /**
     * @return the sendWait
     */
    public int getSendWait() {
        return sendWait;
    }

    /**
     * @param sendWait the sendWait to set
     */
    public void setSendWait(int sendWait) {
        this.sendWait = sendWait;
    }

    public void sys_Initial() {
        items = new HashMap<>();
    }

    public int sys_Open(CommType commType, String target, int port) {
        int dev = 0;
        if (commType == commType.UDP) {
            UDPPackage udpPackage = new UDPPackage();
            dev = items.size() + 1;
            if (items.containsKey(dev) == true) {
                return -1;
            }
            if (udpPackage.open(target, port) == false) {
                return -1;
            }
            items.put(dev, udpPackage);
        } else {
//                string com = target.ToLower();
//                if (com.StartsWith("com") == false) return -1;
//                if (com.Replace("com", "").IsNumber() == false) return -1;
//
//                COMPackageHelper packageHelper = new COMPackageHelper();
//                if (_items.ContainsKey(dev) == true)
//                {
//                    return -1;
//                }
//                if (packageHelper.Open(target, port) == false) return -1;
//                _items.Add(dev, packageHelper);
//                return -1;
        }
        return dev;
    }

    public void sys_Close(int dev) {
        if (items.containsKey(dev) == false) {
            return;
        }
        items.get(dev).close(dev);
        items.remove(dev);
    }

    protected int sys_Send(int dev, Byte[] data, Byte[] rev) {
        rev = null;
        if (items == null) {
            return -1;
        }
        if (!items.containsKey(dev)) {
            return -1;
        }
        items.get(dev).setSendWait(getSendWait());
        byte[]sendBuffer = BytesUtils.tobytes(data);
        byte[]revBuffer = new byte[1024];
        boolean result = items.get(dev).send(sendBuffer, revBuffer);
        if (result == false) {
            return -1;
        }
        rev = BytesUtils.toBytes(revBuffer);
        return 0;
    }

    protected Byte[] CreatePackage(int header,short addr,  int footer, short id, short commandId, short packageCount, short packageNum, Byte[] data) {
        //byte[] packageItem = new byte[25 + 1024];
        ArrayList<Byte> packageItem = new ArrayList<>();
        Byte[] headers = IntUtils.getBytes(header, true);
        List<Byte> headers_sub_1_3 = BytesUtils.getSubArrayAsList(headers, 1, 3);
        packageItem.addAll(headers_sub_1_3);
        //数据包总长度(LEN)[3-4] 2字节，高字节在前
        short packageLen = 0;//占位，所有数据填充完毕后再做填充
        List<Byte> packageLens = ShortUtils.getBytesAsList(packageLen, true);
        packageItem.addAll(packageLens);

        //设备地址[5 - 6] 2字节	高字节为地址X,低字节为地址Y
        //packageItem.AddRange(addr.GetBytes());
        packageItem.addAll(ShortUtils.getBytesAsList(addr, true));
        //数据包识别号[7-8] 2字节,用于识别是哪个设备发出的数据包
//        packageItem.AddRange(id.GetBytes());
        packageItem.addAll(ShortUtils.getBytesAsList(id, true));
        //命令号[9 - 10] 2字节 高字节在前   区分不同的命令
//        packageItem.AddRange(commandId.GetBytes());
        packageItem.addAll(ShortUtils.getBytesAsList(commandId, true));
        //预留字段[11 - 14] 4字节 默认为0
//        packageItem.AddRange(((UInt32) 0).GetBytes());
        packageItem.addAll(IntUtils.getBytesAsList(0, true));
        //总包数[15-16]	2字节 取值范围1到65535	单包命令默认为1
        packageItem.addAll(ShortUtils.getBytesAsList(packageCount, true));
        //当前包序号[17-18] 2字节	取值范围1到65535	单包命令默认为1，序号从1开始计数。
        packageItem.addAll(ShortUtils.getBytesAsList(packageNum, true));
        //命令执行结果[19]   1字节
        //1 =成功
        //2 = 忙碌中
        //3 = 未知命令
        //4 = 校验错误
        //5 = 长度错误
        //6 = 地址错误  MCU反馈命令执行情况
        packageItem.add((byte) 0);
        //数据段[20到LEN - 6] 
        //数据段以外的长度为25字节。
        //可用来保存上位机发给下位机的参数，也可以用来保存下位机返回给上位机的数据。当数据段长度为0时，表示该命令不需要传输数据
        packageItem.addAll(BytesUtils.toList(data));

        //校验码[LEN-5 到LEN-4]	0-65535，高字节在前	采用累加和校验法，将字节序号3到序号(LEN-6)的所有数据相加，取低16位，高8位在前
        short checkSum = 0;//占位
        packageItem.addAll(ShortUtils.getBytesAsList(checkSum, true));

        //数据包尾[LEN-3到LEN-1]	0x5571BD	3字节，高字节在前
        //packageItem.addAll(footer.GetBytes().GetSubArray(1, 3));
        Byte[] footerBytes = IntUtils.getBytes(footer, true);
        List<Byte> footer_sub_1_3 = BytesUtils.getSubArrayAsList(footerBytes, 1, 3);
        packageItem.addAll(footer_sub_1_3);
        short packageTotalLen = (short) packageItem.size();
        Byte[] packageTotalLenByte = ShortUtils.getBytes(packageTotalLen, true);

        packageItem.set(3, packageTotalLenByte[0]);
        packageItem.set(4, packageTotalLenByte[1]);
//        checkSum = (UInt16) (packageItem.ToArray().CheckSumForUInt32(3, packageItem.Count - 3 - 5) & 0xFFFF);
        checkSum =(short)(BytesUtils.sum((Byte[])packageItem.toArray(new Byte[0]), 3, packageItem.size()-3-5) & 0x00FF);
//        byte[] checkSumByte = checkSum.GetBytes();
        Byte[]checkSumByte = ShortUtils.getBytes(checkSum, true);
        packageItem.set(packageItem.size() - 5, checkSumByte[0]);
         packageItem.set(packageItem.size() - 4, checkSumByte[1]);

        return (Byte[])packageItem.toArray(new Byte[0]);
    }

}

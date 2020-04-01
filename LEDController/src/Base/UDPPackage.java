package Base;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Base.BasePackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 *
 * @author Administrator
 */
public class UDPPackage extends BasePackage {
    
    private static final int ECHOMAX = 1024;
    
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private InetAddress address;
    private int port;
    
    public UDPPackage() {
        try {
            datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            
        }
    }
    
    public boolean open(String remote, int remotePort) {
        try {
            datagramSocket.setSoTimeout(5000);
            address = InetAddress.getByName(remote);
            datagramSocket.connect(address, remotePort);
            datagramPacket = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
            port = remotePort;
        } catch (SocketException e) {
            return false;
        } catch (UnknownHostException ex) {
            return false;
        }
        return true;
    }
    
    public void close(int dev) {
        address = null;
        datagramSocket.close();
        datagramSocket = null;
        datagramPacket = null;
    }
    
    public boolean send(byte[] buf, byte[] receive) {
        receive = null;
        try {
            datagramPacket = new DatagramPacket(buf, buf.length, address, port);
            datagramSocket.send(datagramPacket);
            Thread.currentThread().sleep(getSendWait());
            datagramSocket.receive(datagramPacket);
            receive = datagramPacket.getData();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

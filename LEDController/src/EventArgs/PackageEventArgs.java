/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventArgs;

import Enums.PackageInOut;

/**
 *
 * @author Administrator
 */
public class PackageEventArgs {
    
    private byte[]buffer;
    private int dev;
    private PackageInOut packageInOut;

    /**
     * @return the buffer
     */
    public byte[] getBuffer() {
        return buffer;
    }

    /**
     * @param buffer the buffer to set
     */
    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    /**
     * @return the dev
     */
    public int getDev() {
        return dev;
    }

    /**
     * @param dev the dev to set
     */
    public void setDev(int dev) {
        this.dev = dev;
    }

    /**
     * @return the packageInOut
     */
    public PackageInOut getPackageInOut() {
        return packageInOut;
    }

    /**
     * @param packageInOut the packageInOut to set
     */
    public void setPackageInOut(PackageInOut packageInOut) {
        this.packageInOut = packageInOut;
    }
    

}

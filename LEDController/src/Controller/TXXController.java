package Controller;

import Base.TXXBaseController;
import Enums.PackageInOut;
import EventSources.PackageEventSource;
import EventSources.ProgressEventSource;
import Utils.BytesUtils;
import Utils.ShortUtils;
import java.util.ArrayList;
import java.util.EventListener;
import Interfaces.IPackageEventListener;
import Interfaces.IProgressEventListener;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class TXXController extends TXXBaseController {
    
    private PackageEventSource packageDS;
    private ProgressEventSource progressDS;
    
    private static final int SECTOR_SIZE = 1024;
    private boolean logOn = false;
    private boolean showPackage = false;
    private int packageDelay;
    
    private EventListener context;
    
    public TXXController(EventListener context) {
        this.context = context;
        packageDS = new PackageEventSource();
        packageDS.addPackageEventListener((IPackageEventListener)context);
        
        progressDS = new ProgressEventSource();
        progressDS.addPackageEventListener((IProgressEventListener)context);
    }

    /**
     * @return the showPackage
     */
    public boolean isShowPackage() {
        return showPackage;
    }

    /**
     * @param showPackage the showPackage to set
     */
    public void setShowPackage(boolean showPackage) {
        this.showPackage = showPackage;
    }
    
    private void feedbackPackageToUI(int dev, Byte[] buffer, PackageInOut packageInOut) {
        if (showPackage) {
            packageDS.feedbackData(dev, BytesUtils.tobytes(buffer), packageInOut);
        }
    }
    
    private void feedProgressToUI(int dev,int percent,String message)
    {
        progressDS.feedbackData(dev, percent, message);
    }
    
    public int setBrightness(int dev, short addr, short id, byte color, short r, short g, short b) {
        int test=0;
        int header = 0xAA8E42;
        int footer = 0x5571BD;
        short commandId = 0x0002;
        ArrayList<Byte> packageItem = new ArrayList<>();
        packageItem.add(color);
        packageItem.addAll(ShortUtils.getBytesAsList(r, true));
        packageItem.addAll(ShortUtils.getBytesAsList(g, true));
        packageItem.addAll(ShortUtils.getBytesAsList(b, true));
        //int header,short addr,  int footer, short id, short commandId, short packageCount, short packageNum, Byte[] data
        Byte[] buffer = CreatePackage(header, addr, footer, id, commandId, (short) 1, (short) 1, packageItem.toArray(new Byte[0]));
        feedbackPackageToUI(dev, buffer, PackageInOut.Input);
        Byte[] rev = null;
        if (this.sys_Send(dev, buffer, rev) == 0) {
            feedbackPackageToUI(dev, buffer, PackageInOut.Output);
        }
        return 0;
    }
    
    public int uploadMCU(int dev, short addr, short id, byte chip, byte mode, String mcuFile) throws InterruptedException {
        for(int i=0;i<100;i++)
        {
            feedProgressToUI(dev,(i+1),"更新MCU程序:" + (i+1));
            Thread.currentThread().sleep(20);
        }
        
        return 0;
    }

    /**
     * @return the packageDelay
     */
    public int getPackageDelay() {
        return packageDelay;
    }

    /**
     * @param packageDelay the packageDelay to set
     */
    public void setPackageDelay(int packageDelay) {
        this.packageDelay = packageDelay;
    }
    
}

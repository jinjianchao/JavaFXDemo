/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventSources;

import EventArgs.PackageEventArgs;
import Enums.PackageInOut;
import Events.PackageEvent;
import java.util.Iterator;
import java.util.Vector;
import Interfaces.IPackageEventListener;

/**
 *
 * @author Administrator
 */
public class PackageEventSource {

    private Vector list = new Vector();

    public PackageEventSource() {
        super();
    }

    public void addPackageEventListener(IPackageEventListener listener) {
        list.add(listener);
    }

    public void deletePackageEventListener(IPackageEventListener listener) {
        list.remove(listener);
    }

    public void notifyPakageEvent(PackageEvent event) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            //在类中实例化自定义的监听器对象,并调用监听器方法
            ((IPackageEventListener) it.next()).handlePackageFeedbackEvent(event);
        }
    }

    public void feedbackData(int dev,byte[] buffer,PackageInOut inOrOut) {
        PackageEventArgs eventArgs = new PackageEventArgs();
        eventArgs.setBuffer(buffer);
        eventArgs.setDev(dev);
        eventArgs.setPackageInOut(inOrOut);
        notifyPakageEvent(new PackageEvent(this, eventArgs));
    }

}

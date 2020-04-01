/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventSources;

import EventArgs.ProgressEventArgs;
import Events.ProgressEvent;
import java.util.Iterator;
import java.util.Vector;
import Interfaces.IProgressEventListener;

/**
 *
 * @author Administrator
 */
public class ProgressEventSource {
    
    private Vector list = new Vector();

    public ProgressEventSource() {
        super();
    }

    public void addPackageEventListener(IProgressEventListener listener) {
        list.add(listener);
    }

    public void deletePackageEventListener(IProgressEventListener listener) {
        list.remove(listener);
    }

    public void notifyPakageEvent(ProgressEvent event) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            //在类中实例化自定义的监听器对象,并调用监听器方法
            ((IProgressEventListener) it.next()).handleProgressEvent(event);
        }
    }

    public void feedbackData(int dev,int percent,String message) {
        ProgressEventArgs eventArgs = new ProgressEventArgs();
        eventArgs.setPercent(percent);
        eventArgs.setDev(dev);
        eventArgs.setMessage(message);
        notifyPakageEvent(new ProgressEvent(this, eventArgs));
    }
}

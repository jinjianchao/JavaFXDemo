/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Events;

import EventArgs.PackageEventArgs;
import java.util.EventListener;
import java.util.EventObject;

/**
 *
 * @author Administrator
 */
public class PackageEvent extends EventObject {
    
    private PackageEventArgs args;
    
    /**
     *
     * @param source
     * @param args
     */
    public PackageEvent(Object source,PackageEventArgs args) {
        super(source);
        this.args = args;
    }
    
    public PackageEventArgs getArgs()
    {
        return this.args;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Events;

import EventArgs.ProgressEventArgs;
import java.util.EventObject;

/**
 *
 * @author Administrator
 */
public class ProgressEvent extends EventObject {
    private ProgressEventArgs args;
            
    public ProgressEvent(Object source,ProgressEventArgs args) {
        super(source);
        this.args = args;
    }

    /**
     * @return the args
     */
    public ProgressEventArgs getArgs() {
        return args;
    }

    /**
     * @param args the args to set
     */
    public void setArgs(ProgressEventArgs args) {
        this.args = args;
    }
    
}

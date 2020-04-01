/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base;

/**
 *
 * @author Administrator
 */
public abstract class BasePackage {
    private int sendWait;
    
         public boolean open(String remote, int remotePort)
        {
            return false;
        }

        public void close(int dev)
        {

        }

        public boolean send(byte[] buf, byte[] receive)
        {
            receive = null;
            return false;
        }

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
}

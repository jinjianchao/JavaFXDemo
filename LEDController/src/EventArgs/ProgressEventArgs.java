/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventArgs;

/**
 *
 * @author Administrator
 */
public class ProgressEventArgs {

    private int percent;
    private int dev;
    private String message;

    /**
     * @return the percent
     */
    public int getPercent() {
        return percent;
    }

    /**
     * @param percent the percent to set
     */
    public void setPercent(int percent) {
        this.percent = percent;
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
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
}

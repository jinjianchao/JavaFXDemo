/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Events.PackageEvent;
import java.util.EventListener;

/**
 *
 * @author Administrator
 */
public interface IPackageEventListener extends EventListener {

    void handlePackageFeedbackEvent(PackageEvent packageEvent);
}

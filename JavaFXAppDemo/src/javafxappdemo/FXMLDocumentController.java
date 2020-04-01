/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxappdemo;

import Controller.TXXController;
import Enums.CommType;
import Events.PackageEvent;
import Events.ProgressEvent;
import Utils.BytesUtils;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.web.HTMLEditor;
import Interfaces.IPackageEventListener;
import Interfaces.IProgressEventListener;

/**
 *
 * @author Administrator
 */
public class FXMLDocumentController implements Initializable, IPackageEventListener, IProgressEventListener {

//    private PackageEventSource ds;
    TXXController txxOper;

    @FXML
    private Slider sliderBrightness;

    @FXML
    private Button buttonSendBrightness;

    @FXML
    private HTMLEditor hdmlEdit;

    @FXML
    private CheckBox ckShowPackage;

    @FXML
    private Button btnProgressTest;

    @FXML
    Label lblProgressMsg;

    @FXML
    ProgressBar progressPercent;

    @FXML
    private void handleShowPackageData(ActionEvent event) {
        txxOper.setShowPackage(ckShowPackage.isSelected());
    }

    @FXML
    private void handleSendBrightnessAction(ActionEvent event) {
        double r, g, b;
        r = g = b = 65535 * (sliderBrightness.getValue() / 100);
        int dev = txxOper.sys_Open(CommType.UDP, "192.168.0.32", 8001);
        txxOper.setBrightness(dev, (short) 0, (short) 0, (byte) 0, (short) r, (short) g, (short) b);
        txxOper.sys_Close(dev);
    }

    @FXML
    private void handleProgressTestAction(ActionEvent event) {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                int dev = txxOper.sys_Open(CommType.UDP, "192.168.0.32", 8001);
//                try {
//                    txxOper.uploadMCU(dev, (short) 0, (short) 0, (byte) 0, (byte) 0, "");
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                txxOper.sys_Close(dev);
//            }
//
//        };

//                int dev = txxOper.sys_Open(CommType.UDP, "192.168.0.32", 8001);
//                try {
//                    txxOper.uploadMCU(dev, (short) 0, (short) 0, (byte) 0, (byte) 0, "");
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                txxOper.sys_Close(dev);

//        thread.setName("thread1");
//        thread.start();

        Task<Void> progressTask = new Task<Void>(){

            @Override
            protected void succeeded() {
                super.succeeded();

            }

            @Override
            protected void cancelled() {
                super.cancelled();

            }

            @Override
            protected void failed() {
                super.failed();

            }

            @Override
            protected Void call() throws Exception {
                int dev = txxOper.sys_Open(CommType.UDP, "192.168.0.32", 8001);
                try {
                    txxOper.uploadMCU(dev, (short) 0, (short) 0, (byte) 0, (byte) 0, "");
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                txxOper.sys_Close(dev);
                return null;
            }
        };
        new Thread(progressTask).start();
    }

    public FXMLDocumentController() {
        txxOper = new TXXController(this);
        txxOper.setSendWait(100);
        txxOper.sys_Initial();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void handlePackageFeedbackEvent(PackageEvent packageEvent) {
        byte[] bt = packageEvent.getArgs().getBuffer();
        String tmp = BytesUtils.toHexString(bt, " ");
        String hdml = hdmlEdit.getHtmlText();
        hdmlEdit.setHtmlText(hdml + tmp + "\n");
    }

    @Override
    public void handleProgressEvent(ProgressEvent progressEvent) {
        Platform.runLater(()->{
            progressPercent.setProgress(progressEvent.getArgs().getPercent()/100d);
            lblProgressMsg.setText(progressEvent.getArgs().getMessage());
        });
    }
}

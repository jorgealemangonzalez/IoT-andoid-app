package com.jorge.arnau.iot;

import org.w3c.dom.Document;

public class RFIDManager {
    private static RFIDManager theInstance = null;
    private String urlConnexion = "http://10.0.2.2:3161";
    private String urlDevices = urlConnexion+"/devices";
    private Document devicesDoc = null;

    private RFIDManager(){

    }

    public static RFIDManager getInstance(){
        if(RFIDManager.theInstance == null)
            RFIDManager.theInstance = new RFIDManager();
        return RFIDManager.theInstance;
    }

    public void readDevices(){
        FetchHttpRequest req = new FetchHttpRequest();
        Document doc = req.execute(this.urlDevices);
    }
}

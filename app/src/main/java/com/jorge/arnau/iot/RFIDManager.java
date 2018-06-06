package com.jorge.arnau.iot;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilderFactory;


public class RFIDManager {
    private static RFIDManager theInstance = null;
    private String urlConnexion = "http://192.168.2.152:3161";   //TODO PUEDE QUE HAYA QUE CAMBIARLO CON UN MOVIL REAL
    private URL urlDevices = new URL(urlConnexion+"/devices/AdvanReader-m4-150/inventory");  //TODO simulator cambia en cada uno (obtener con llamada inicial /devices ), iniciar device con /devices/{dev_id}/start
    private Document devicesDoc = null;

    private RFIDManager() throws MalformedURLException {

    }

    public static RFIDManager getInstance(){
        if(RFIDManager.theInstance == null) {
            try {
                RFIDManager.theInstance = new RFIDManager();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return RFIDManager.theInstance;
    }

    public void readDevices(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            Document doc = factory.newDocumentBuilder().parse(urlDevices.openStream());
            this.devicesDoc = doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashSet<String> getConnectedDevices(){
        NodeList devices =  this.devicesDoc.getElementsByTagName("item");
        HashSet<String> resutIds = new HashSet<>();
        for( int i = 0 ; i < devices.getLength(); ++i){
            Element device = (Element) devices.item(i);
            String id = ((Element)device.getElementsByTagName("epc").item(0)).getTextContent();
            Log.i("GET CONNECTED DEVICES", id);
            resutIds.add(id);
        }
        return resutIds;
    }
}

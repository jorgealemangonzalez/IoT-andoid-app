package com.jorge.arnau.iot;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.StringWriter;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class RFIDManager {
    private static RFIDManager theInstance = null;
    private String urlConnexion = "http://10.0.2.2:3161";   //TODO PUEDE QUE HAYA QUE CAMBIARLO CON UN MOVIL REAL
    private URL urlDevices = new URL(urlConnexion+"/devices/simulator/inventory");
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

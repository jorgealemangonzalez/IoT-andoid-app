package com.jorge.arnau.iot;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.ContentHandlerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.net.URLConnection;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class UpdateDataAsync extends AsyncTask<String, Integer, Long> {
    public String adress = "http://10.0.2.2:3161/devices";
    public HashMap<String,LocalDateTime> connected_devices;


    private String XMLDocumentAsString(Document doc){
        //XML as string

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        try {
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "")

        // end XML as string
        return output;
    }

    protected Long doInBackground(String... urls) {
        long totalSize = 0;
        boolean exitLoop = false;
        while (!exitLoop){
            RFIDManager manager = RFIDManager.getInstance();
            manager.readDevices();
            HashSet<String> listDevices = manager.getConnectedDevices();
            Log.i("DATA", listDevices.toString());

            for(String device : listDevices){
                if(connected_devices.get(device) != null){
                    //todo
                }
            }

            //Don't use all CPU
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                exitLoop = true;
                e.printStackTrace();
            }
        }
        return totalSize;
        /*for (int i = 0; i < count; i++) {
            try {
                URL url = new URL(urls[0]);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                Document doc = factory.newDocumentBuilder().parse(url.openStream());

                Log.i("XML full", XMLDocumentAsString(doc));
                //Log.i("XML DATA: ", doc.getElementsByTagName("from").item(0).getTextContent());
                //Log.i("UpdateDataAsync", "HOLA HTTP");
            } catch (Exception e) {
                e.printStackTrace();
            }


                //totalSize += Downloader.downloadFile(urls[i]);
                //publishProgress((int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                //if (isCancelled()) break;

        }
        return totalSize;*/
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        Log.i("UpdateDataAsync", "ADIOS HTTP");
        //showDialog("Downloaded " + result + " bytes");
    }
}

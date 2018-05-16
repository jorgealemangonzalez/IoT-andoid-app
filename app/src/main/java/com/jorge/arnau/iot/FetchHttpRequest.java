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

public class FetchHttpRequest extends AsyncTask<String, Integer, Long> {
    public String adress = "http://10.0.2.2:3161/devices";

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
        int count = urls.length;
        long totalSize = 0;
        for (int i = 0; i < count; i++) {
            try {
                URL url = new URL(urls[0]);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                Document doc = factory.newDocumentBuilder().parse(url.openStream());

                Log.i("XML full", XMLDocumentAsString(doc));
                //Log.i("XML DATA: ", doc.getElementsByTagName("from").item(0).getTextContent());
                //Log.i("FetchHttpRequest", "HOLA HTTP");
            } catch (Exception e) {
                e.printStackTrace();
            }

                /*
                totalSize += Downloader.downloadFile(urls[i]);
                publishProgress((int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                if (isCancelled()) break;*/

        }
        return totalSize;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        Log.i("FetchHttpRequest", "ADIOS HTTP");
        //showDialog("Downloaded " + result + " bytes");
    }
}

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
import java.net.ContentHandlerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.DocumentBuilderFactory;

public class FetchHttpRequest extends AsyncTask<String, Integer, Long> {
    public String adress = "https://www.w3schools.com/xml/note.xml";


    protected Long doInBackground(String... urls) {
        int count = urls.length;
        long totalSize = 0;
        for (int i = 0; i < count; i++) {
            try {
                URL url = new URL(urls[0]);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                Document doc = factory.newDocumentBuilder().parse(url.openStream());
                Log.i("JSON: ", doc.getElementsByTagName("from").item(0).getTextContent());
                Log.i("FetchHttpRequest", "HOLA HTTP");
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

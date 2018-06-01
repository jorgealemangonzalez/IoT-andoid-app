package com.jorge.arnau.iot;

import android.os.AsyncTask;
import android.util.Log;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class UpdateDataAsync extends AsyncTask<String, Integer, Long> {
    public String adress = "http://10.0.2.2:3161/devices";
    public HashMap<String,LocalDateTime> connected_devices = new HashMap<>();

    protected Long doInBackground(String... urls) {

        long totalSize = 0;
        boolean exitLoop = false;
        while (!exitLoop){
            RFIDManager manager = RFIDManager.getInstance();
            manager.readDevices();
            HashSet<String> listDevices = manager.getConnectedDevices();
            Log.i("DATA", listDevices.toString());

            LocalDateTime now = LocalDateTime.now();

            // devides which disconnected
            Collection<String> toRemove = new HashSet<>();
            toRemove.addAll(connected_devices.keySet());
            toRemove.removeAll(listDevices);
            for(String device: toRemove){
                connected_devices.remove(device);
                MainActivity.mydb.updateDeviceEndTime(device, now);
            }

            // new devices connected
            for(String device : listDevices){
                if(connected_devices.get(device) == null){
                    connected_devices.put(device, now);
                    MainActivity.mydb.insertDevice(device, now);
                }
            }

            List<Course> courses = MainActivity.mydb.getAllCourses();

            //Don't use all CPU
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                exitLoop = true;
                e.printStackTrace();
            }
        }
        return totalSize;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        Log.i("UpdateDataAsync", "ADIOS HTTP");
        //showDialog("Downloaded " + result + " bytes");
    }
}

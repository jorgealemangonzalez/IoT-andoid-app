package com.jorge.arnau.iot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayDevices extends AppCompatActivity {
    int id_To_Update = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_devices);

        Bundle extras = getIntent().getExtras();
        //Log.i("ADD", extras.toString());
        if(extras !=null) {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                id_To_Update = Value;
            }
        }
    }


    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {

            Log.i("tag:", extras.toString());
        }
    }
}

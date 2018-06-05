package com.jorge.arnau.iot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayDevices extends AppCompatActivity {
    int id_To_Update = 0;
    private TagDataAdapter tagDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_devices);

        Bundle extras = getIntent().getExtras();
        /*Log.i("ADD", extras.toString());
        TextView test = (TextView) findViewById(R.id.NENE);
        test.setText("LALALAL");
        test.setFocusable(false);
        test.setClickable(false);*/

        tagDataAdapter= new TagDataAdapter(this, new ArrayList<TagData>());
        final ListView recordsView = (ListView) findViewById(R.id.records_view);
        recordsView.setAdapter(tagDataAdapter);
    }


    public void run(View view) {
        Bundle extras = getIntent().getExtras();
    }
}

package com.jorge.arnau.iot;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TagDataAdapter extends BaseAdapter {

    private Context recordContext;
    private List<TagData> recordList;
    private static class RecordViewHolder {
        public TextView tagIdView;
        public TextView timeView;
    }

    public TagDataAdapter(Context context, List<TagData> records) {
        recordList = records;
        recordContext = context;
    }

    public void add(TagData record) {
        recordList.add(record);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return recordList.size();
    }
    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecordViewHolder holder;

        if (view ==null){
            LayoutInflater recordInflater = (LayoutInflater)
                    recordContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = recordInflater.inflate(R.layout.record, null);

            holder = new RecordViewHolder();
            holder.tagIdView = (TextView) view.findViewById(R.id.record_tag_id);
            holder.timeView = (TextView) view.findViewById(R.id.record_time);
            view.setTag(holder);

        }else {
            holder = (RecordViewHolder) view.getTag();
        }

        TagData record = (TagData) getItem(i);
        holder.tagIdView.setText(record.tag_id);
        holder.timeView.setText(record.time);
        return view;
    }
}



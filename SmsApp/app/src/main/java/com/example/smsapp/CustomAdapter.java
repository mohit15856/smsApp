package com.example.smsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mohit.Aggarwal on 2/24/2017.
 */

public class CustomAdapter extends BaseAdapter {

    private List<SMSData> smsDatas;
    private Context mContext;
    private SMSData smsData;
    private LayoutInflater mInflater;

    public CustomAdapter(Context mContext, List<SMSData> smsDatas )
    {
        this.smsDatas = smsDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return smsDatas!=null ? smsDatas.size() : 0 ;
    }

    @Override
    public Object getItem(int i) {
        return smsDatas!=null ? smsDatas.get(i) : 0 ;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = vi.inflate(R.layout.contact_list_item, null);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView msgBody = (TextView) convertView.findViewById(R.id.msgBody);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        smsData = smsDatas.get(i);

        name.setText(smsData.getNumber());
        msgBody.setText(smsData.getBody());
        date.setText(smsData.getDate());



        return convertView;
    }
}

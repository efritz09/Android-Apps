package xyz.efritz.bikecurious;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Eric on 4/9/2016.
 */
public class BikeHistoryAdapter extends ArrayAdapter {
    Context context;
    int layoutResourceId;
    ArrayList<Ride> data;


    public BikeHistoryAdapter(Context context, int layoutResourceID, ArrayList<Ride> data) {
        super(context, layoutResourceID, data);
        this.layoutResourceId = layoutResourceID;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId,parent,false);
            holder = new ViewHolder();
            holder.textView1 = (TextView)row.findViewById(R.id.user_text1);
            holder.textView2 = (TextView)row.findViewById(R.id.user_text2);
            holder.icon = (ImageView)row.findViewById(R.id.icon_history);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder)row.getTag();
        }
        Ride thisRide = data.get(position);
//        String data_string = data.get(position);
        holder.textView1.setText(thisRide.location);
//        String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
//        date.add(currentDateTime);
//        holder.textView2.setText(date.get(position));
//        String date_string = date.get(position);
        holder.textView2.setText(thisRide.date);
        Picasso.with(context).load(R.mipmap.charizard).into(holder.icon);

        return row;
    }


    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageView icon;
    }

//    This has to be static or else shit will go wrong for some reason in RideHistory.java
    static class Ride {
        String location;
        String date;
        int imageID;
    }

}

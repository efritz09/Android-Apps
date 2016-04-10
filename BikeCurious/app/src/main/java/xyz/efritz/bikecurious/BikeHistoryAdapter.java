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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Eric on 4/9/2016.
 */
public class BikeHistoryAdapter extends ArrayAdapter {
    Context context;
    int layoutResourceId;
    ArrayList<String> data;

    public BikeHistoryAdapter(Context context, int layoutResourceID, ArrayList<String> data) {
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
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder)row.getTag();
        }

        String string = data.get(position);
        holder.textView1.setText(string);
        String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
        holder.textView2.setText(currentDateTime);
        Picasso.with(context).load(R.drawable.charizard).into(holder.icon);
//        holder.icon.setImageResource(R.drawable.charizard);


        return row;
    }

    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageView icon;
    }

}

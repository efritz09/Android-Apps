package xyz.efritz.bikecurious;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RideHistory extends Activity {

    String[] dick = {"dick","balls"};
    ArrayList<String> rides = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_history);


        ListView listView;
        BikeHistoryAdapter arrayAdapter;
        listView = (ListView)findViewById(R.id.listView_history);
        arrayAdapter = new BikeHistoryAdapter(this,R.layout.history_user,rides);
        listView.setAdapter(arrayAdapter);

        //use this to get click shit
//        public void onItemClickListener(AdapterView<> parent, View v, int position, long id) {

    }

    public void click_add_ride(View view) {
        Context context = getApplicationContext();

        EditText new_ride = (EditText)findViewById(R.id.editText_add_ride);
        if(new_ride.getText().toString().equals("")) Toast.makeText(context, R.string.add_ride_empty_toast,Toast.LENGTH_SHORT).show();
        else {
            rides.add(new_ride.getText().toString());
        }
    }
}

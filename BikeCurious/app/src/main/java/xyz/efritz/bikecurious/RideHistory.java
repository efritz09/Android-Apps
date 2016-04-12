package xyz.efritz.bikecurious;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class RideHistory extends Activity {
//    It's in this form because Ride is in BikeHistoryAdapter.java
    static ArrayList<BikeHistoryAdapter.Ride> ride_history = new ArrayList<>();
//    ArrayList<String> times = new ArrayList<String>();
    int[] smash = {R.mipmap.dk,R.mipmap.falcon,R.mipmap.fox,R.mipmap.jiggly,R.mipmap.kirby,
                    R.mipmap.link,R.mipmap.luigi,R.mipmap.mario,R.mipmap.ness,R.mipmap.pikachu,
                    R.mipmap.samus,R.mipmap.yoshi};
    final int smash_max = 12;
    Random random = new Random();

    BikeHistoryAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_history);


        ListView listView = (ListView)findViewById(R.id.listView_history);
        arrayAdapter = new BikeHistoryAdapter(this,R.layout.history_user,ride_history);
        listView.setAdapter(arrayAdapter);

        //use this to get click shit
//        public void onItemClickListener(AdapterView<> parent, View v, int position, long id) {

        ImageView imageView = (ImageView)findViewById(R.id.history_user_image);
        Picasso.with(this).load(R.drawable.face).fit().transform(new CircleTransform()).into(imageView); //.transform(new CircleTransform())
    }

    public void click_add_ride(View view) {
        Context context = getApplicationContext();

        EditText new_ride = (EditText)findViewById(R.id.editText_add_ride);
        if(new_ride.getText().toString().equals("")) Toast.makeText(context, R.string.add_ride_empty_toast,Toast.LENGTH_SHORT).show();
        else {
            BikeHistoryAdapter.Ride ride = new BikeHistoryAdapter.Ride();
            ride.location = new_ride.getText().toString();
            ride.date = DateFormat.getDateTimeInstance().format(new Date());
            int randomNumber = random.nextInt(smash_max);

            ride.imageID = smash[randomNumber];
            ride_history.add(ride);
//            rides.add(new_ride.getText().toString());
//            times.add(DateFormat.getDateTimeInstance().format(new Date()));
            arrayAdapter.notifyDataSetChanged();
        }
    }
}



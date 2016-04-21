package xyz.efritz.bikecurious;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class RideHistoryActivity extends Activity {
    static final String TAG = "RideHistoryActivity";
    static ArrayList<Ride> ride_history;
    int[] smash = {R.mipmap.dk, R.mipmap.falcon, R.mipmap.fox, R.mipmap.jiggly, R.mipmap.kirby,
            R.mipmap.link, R.mipmap.luigi, R.mipmap.mario, R.mipmap.ness, R.mipmap.pikachu,
            R.mipmap.samus, R.mipmap.yoshi};
    final int smash_max = 12;
    Random random = new Random();
    BikeHistoryAdapter arrayAdapter;
    HistoryDatabaseAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_history);
        //open the database and populate the list if the history exists
        historyAdapter = new HistoryDatabaseAdapter(this);
        historyAdapter = historyAdapter.open();
        if(historyAdapter.isEmpty()) ride_history = new ArrayList<>();
        else ride_history = historyAdapter.getAllEntries();

        //setup the listview with the ride_history
        ListView listView = (ListView) findViewById(R.id.listView_history);
        arrayAdapter = new BikeHistoryAdapter(this, R.layout.history_user, ride_history);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                delete_dialog(pos);
                return false;
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.history_user_image);
        Picasso.with(this).load(R.drawable.face).fit().transform(new CircleTransform()).into(imageView);
    }

    /*
    delete_dialog
    allows the user to delete the selected ride
    FUTURE: let the user change the name/icon?
     */
    public void delete_dialog(int pos) {
        Context context = RideHistoryActivity.this;
        final int index = pos;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.delete_ride, null);
        builder.setView(dialogView);
        builder.setTitle("Delete Ride?");
        builder.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ride ride = ride_history.get(index);
                ride_history.remove(index);
                historyAdapter.deleteEntry(ride.date);
                arrayAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*
    click_add_ride
    allows the user to add a ride with a location name
    FUTURE: let the user select the icon?
     */
    public void click_add_ride(View view) {
        EditText new_ride = (EditText) findViewById(R.id.editText_add_ride);
        Ride ride = new Ride();
        if (new_ride.getText().toString().equals("")) {
            ride.location = getString(R.string.new_ride_empty_location);
        } else {
            ride.location = new_ride.getText().toString();
        }
        ride.date = DateFormat.getDateTimeInstance().format(new Date());
        int randomNumber = random.nextInt(smash_max);
        ride.imageID = smash[randomNumber];

        //add to array adapter and database
        ride_history.add(0, ride);
        historyAdapter.insertEntry(ride.location, ride.date, ride.imageID);

        new_ride.getText().clear();
        arrayAdapter.notifyDataSetChanged();
        //close the keyboard
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
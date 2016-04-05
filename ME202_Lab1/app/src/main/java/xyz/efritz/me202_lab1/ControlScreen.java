package xyz.efritz.me202_lab1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

public class ControlScreen extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_screen);

        Switch mode, state;

        mode = (Switch) findViewById(R.id.lightMode);
        state = (Switch) findViewById(R.id.lightState);

        mode.setChecked(true);
        state.setChecked(true);

        mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView mode_switch = (TextView) findViewById(R.id.mode_text);
                if (isChecked) {
//                    Toast.makeText(getApplicationContext(), "mode switch on", Toast.LENGTH_SHORT).show();
                    mode_switch.setText("Auto");
                } else {
//                    Toast.makeText(getApplicationContext(), "mode switch off", Toast.LENGTH_SHORT).show();
                    mode_switch.setText("On");
                }
            }
        });


        state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView state_switch = (TextView) findViewById(R.id.state_text);
                if (isChecked) {
//                    Toast.makeText(getApplicationContext(), "state switch on", Toast.LENGTH_SHORT).show();
                    state_switch.setText("Solid");
                } else {
//                    Toast.makeText(getApplicationContext(), "state switch off", Toast.LENGTH_SHORT).show();
                    state_switch.setText("Blinking");
                }
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void unlock(View view) {
        //open display
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText bikeID = new EditText(getApplicationContext());
        bikeID.setTextColor(ContextCompat.getColor(this, R.color.black));
        bikeID.setHintTextColor(ContextCompat.getColor(this,R.color.grey));
        //Use this if version < 23 (maybe add an if statement later?)
//        bikeID.setTextColor(this.getResources().getColor(R.color.black));
//        bikeID.setHintTextColor(this.getResources().getColor(R.color.grey));

        bikeID.setHint("Bike Address");
        builder.setMessage("Enter Bike to unlock:");
        builder.setTitle("Unlock?");
        builder.setView(bikeID);
        builder.setPositiveButton("accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),Integer.toString(which),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Entered: " + bikeID.getText().toString(), Toast.LENGTH_LONG).show();
                TextView connect = (TextView)findViewById(R.id.state_of_connection);
                connect.setText("Connected: ");
                connect = (TextView)findViewById(R.id.unique_identifier);
                connect.setText(bikeID.getText().toString());
                connect = (TextView)findViewById(R.id.lock_status);
                connect.setText("Unlocked");
                connect.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));

            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(), Integer.toString(which), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void rider_history(View view) {
        Toast.makeText(getApplicationContext(), "no records yet...", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Fatass", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ControlScreen Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://xyz.efritz.me202_lab1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ControlScreen Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://xyz.efritz.me202_lab1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}

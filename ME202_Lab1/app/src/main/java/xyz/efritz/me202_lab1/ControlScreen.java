package xyz.efritz.me202_lab1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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

//        Context context = getApplicationContext();
//        int duration = Toast.LENGTH_SHORT;
//        Toast.makeText(context, "shitbox", duration).show();
        Switch mode, state;


        mode = (Switch) findViewById(R.id.lightMode);
        state = (Switch) findViewById(R.id.lightState);

        mode.setChecked(true);
        state.setChecked(true);

        mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView modeswitch = (TextView) findViewById(R.id.mode_text);
                if (isChecked) {
//                    Toast.makeText(getApplicationContext(), "mode switch on", Toast.LENGTH_SHORT).show();
                    modeswitch.setText("Auto");
                } else {
//                    Toast.makeText(getApplicationContext(), "mode switch off", Toast.LENGTH_SHORT).show();
                    modeswitch.setText("On");
                }
            }
        });

//        if(mode.isChecked()) {
//            Toast.makeText(context, "mode switch on", duration).show();
//        } else {
//            Toast.makeText(context, "mode switch off", duration).show();
//        }
//

        state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView stateswitch = (TextView) findViewById(R.id.state_text);
                if (isChecked) {
//                    Toast.makeText(getApplicationContext(), "state switch on", Toast.LENGTH_SHORT).show();
                    stateswitch.setText("Solid");
                } else {
//                    Toast.makeText(getApplicationContext(), "state switch off", Toast.LENGTH_SHORT).show();
                    stateswitch.setText("Blinking");
                }
            }
        });
//        if(state.isChecked()) {
//            Toast.makeText(context, "mode switch on", duration).show();
//        } else {
//            Toast.makeText(context, "mode switch off", duration).show();
//        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//
//        Button more = (Button)findViewById(R.id.lock_button);
//        more.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//                alertDialog.setTitle("hi");
//                alertDialog.setMessage("test");
//                alertDialog.setButton("Contineu...", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //
//                    }
//                });
//                alertDialog.show();
//            }
//        });
    }

    public void unlock(View view) {
        //open display
//        Context context = getApplicationContext();
        Toast.makeText(getApplicationContext(), "fuck off", Toast.LENGTH_SHORT).show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("hello");
//        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //fuck
//            }
//        });
//        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //fuck
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();


//                AlertDialog.Builder unlock_alert = new AlertDialog.Builder(getApplicationContext());
//        unlock_alert.setMessage("Unlock bike?");
//        unlock_alert.setCancelable(true);

//        unlock_alert.setPositiveButton(
//                "unlock",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                }
//        );
//
//        unlock_alert.setNegativeButton(
//                "cancel",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                }
//        );
//        AlertDialog alert = unlock_alert.create();
//        alert.show();
    }

    public void rider_history(View view) {
        Toast.makeText(getApplicationContext(), "no history yet", Toast.LENGTH_SHORT).show();
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

    //check the light mode switch


}

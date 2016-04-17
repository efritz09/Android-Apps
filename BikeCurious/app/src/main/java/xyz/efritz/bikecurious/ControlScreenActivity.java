package xyz.efritz.bikecurious;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ControlScreenActivity extends Activity {

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
            TextView mode_switch = (TextView) findViewById(R.id.mode_text);

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mode_switch.setText(getString(R.string.modeAuto));
                } else {
                    mode_switch.setText(getString(R.string.modeOn));
                }
            }
        });

        state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            TextView state_switch = (TextView) findViewById(R.id.state_text);

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state_switch.setText(getString(R.string.stateSolid));
                } else {
                    state_switch.setText(getString(R.string.stateBlinking));
                }
            }
        });

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

        bikeID.setHint(getString(R.string.hint_bikeaddr));
        builder.setMessage(getString(R.string.unlockmsg));
        builder.setTitle(getString(R.string.unlockquestion));
        builder.setView(bikeID);
        builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
            Context cont = getApplicationContext();
            TextView connect = (TextView) findViewById(R.id.state_of_connection);
            TextView id = (TextView) findViewById(R.id.unique_identifier);
            TextView lockstat = (TextView) findViewById(R.id.lock_status);

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (bikeID.getText().toString().equals("")) {
                    Toast.makeText(cont, getString(R.string.toast_addr_empty), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(cont, getString(R.string.toast_addr_entered) + bikeID.getText().toString(), Toast.LENGTH_LONG).show();
                    connect.setText(getString(R.string.connected)); //getString(R.string.name)
                    id.setText(bikeID.getText().toString());
                    lockstat.setText(getString(R.string.unlocked));
                    lockstat.setTextColor(ContextCompat.getColor(cont, R.color.red));
                }

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

    public void rider_history(View view) {
        Intent Start_History = new Intent(this, xyz.efritz.bikecurious.RideHistoryActivity.class);
        startActivity(Start_History);
    }

    public void click_logout(View view) {
        Intent login = new Intent(this, xyz.efritz.bikecurious.LoginActivity.class);
        startActivity(login);
        finish();
    }

}

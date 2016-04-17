package xyz.efritz.bikecurious;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
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
    private BluetoothAdapter bluetoothAdapter;
//    private LeDeviceListAdapter mLeDeviceListAdapter;
    private final static int REQUEST_ENABLE_BT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_screen);
/*--------------Code from developer.android.com--------------*/
        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        //ensures bluetooth is available and is enabled
        if(bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Switch mode, state;
        mode = (Switch) findViewById(R.id.lightMode);
        mode.setChecked(true);
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
        state = (Switch) findViewById(R.id.lightState);
        state.setChecked(true);
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

//        private BluetoothAdapter.LeScanCallback mLeScanCallback =
//                new BluetoothAdapter.LeScanCallback() {
//                    @Override
//                    public void onLeScan(final BluetoothDevice device, int rssi,
//                                         byte[] scanRecord) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mLeDeviceListAdapter.addDevice(device);
//                                mLeDeviceListAdapter.notifyDataSetChanged();
//                            }
//                        });
//                    }
//                };
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

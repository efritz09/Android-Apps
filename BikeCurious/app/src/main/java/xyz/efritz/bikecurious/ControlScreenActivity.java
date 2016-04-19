package xyz.efritz.bikecurious;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

public class ControlScreenActivity extends Activity {
    public static String TAG = "ControlScreenActivity";
    final String filterAddr = "E4:F3:CB:6E:57:3E";
    Boolean autoConnectBoolean = true;
    Boolean writingFlag = false;
    BluetoothGattCallback gattCallback;
    BluetoothAdapter.LeScanCallback callback;
    BluetoothGatt gatt;
    BluetoothGattCharacteristic tx;
    BluetoothGattCharacteristic rx;
    public static UUID TX_UUID = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID UART_UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID CLIENT_UUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E");
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_screen);


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


        gattCallback = new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                //connect, disconnect, error handling
                super.onConnectionStateChange(gatt, status, newState);
                if(newState == BluetoothGatt.STATE_CONNECTED) {
                    //connected to device, start discovering services
                    if (!gatt.discoverServices()) {
                        Log.i(TAG, "gatt not discovered");
                        //connectFailure();
                    } else {
                        Log.i(TAG, "gatt discovered");
//                        enableSwitches();
                    }
                }
                else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                    Log.i(TAG, "clean up");
//                    disableSwitches();
                    //clean up connection
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                //set the listener for characteristic. UUIDs
                super.onServicesDiscovered(gatt, status);
                //notify connection failure if service discovery failed
                if(status == BluetoothGatt.GATT_FAILURE) {
                    Log.i(TAG, "GATT FAILURE");
                    return;
                }
                //save reference to each UART characteristic, module level
                //uart_uuid can be equal to tx_uuid
                tx = gatt.getService(UART_UUID).getCharacteristic(TX_UUID);
                //-----------enable notifications
//                if(!gatt.setCharacteristicNotification(tx, true)) {
//                    Log.i(TAG, "Doing something, fuck if I know");
//                    return;
//                }
//                BluetoothGattDescriptor desc = tx.getDescriptor(CLIENT_UUID);
//                if(desc == null) {
//                    //print something
//                    return;
//                }
//                tx.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//                if(!gatt.writeDescriptor(desc)) {
//                    //success
//                    return;
//                }
                //---------------------------//
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                //receiving
                super.onCharacteristicChanged(gatt, characteristic);
                Log.i(TAG,characteristic.getStringValue(0));
                //do something with the data
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                //write complete
                super.onCharacteristicWrite(gatt, characteristic, status);
                Log.i(TAG,"Write complete");
                if(status != BluetoothGatt.GATT_SUCCESS) {
                    Log.i(TAG,"ERROR");
                    //error handling
                }
                writingFlag = false;
            }

            public void disconnect() {
                if(gatt != null) {
                    gatt.disconnect();
                }
                gatt = null;
                tx = null;
                rx = null;
            }
        };
    }

    private void disableSwitches() {
        Switch mode, state;
        mode = (Switch) findViewById(R.id.lightMode);
        state = (Switch) findViewById(R.id.lightState);
        mode.setClickable(true);
        state.setClickable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mode.setThumbTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lessdark)));
            state.setThumbTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lessdark)));
            mode.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lessdark)));
            state.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lessdark)));
        }
        TextView modeTitle, stateTitle;
        modeTitle = (TextView)findViewById(R.id.mode_title);
        stateTitle = (TextView)findViewById(R.id.state_title);
        modeTitle.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lessdark)));
        stateTitle.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lessdark)));
    }
    private void enableSwitches() {
        Switch mode, state;
        mode = (Switch) findViewById(R.id.lightMode);
        state = (Switch) findViewById(R.id.lightState);
        mode.setClickable(false);
        state.setClickable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mode.setThumbTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green)));
            state.setThumbTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green)));
            mode.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkgreen)));
            state.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkgreen)));
        }
        TextView modeTitle, stateTitle;
        modeTitle = (TextView)findViewById(R.id.mode_title);
        stateTitle = (TextView)findViewById(R.id.state_title);
        modeTitle.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey)));
        stateTitle.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey)));

    }

    public void unlock(View view) {
        //open display
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText bikeID = new EditText(getApplicationContext());
        bikeID.setText(filterAddr);
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
                //------------pre-bluetooth code
                if (bikeID.getText().toString().equals("")) {
                    Toast.makeText(cont, getString(R.string.toast_addr_empty), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(cont, getString(R.string.toast_addr_entered) + bikeID.getText().toString(), Toast.LENGTH_LONG).show();
                    connect.setText(getString(R.string.connected)); //getString(R.string.name)
                    id.setText(bikeID.getText().toString());
                    lockstat.setText(getString(R.string.unlocked));
                    lockstat.setTextColor(ContextCompat.getColor(cont, R.color.red));
                }
                //--------------------------//




                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                //starts the scan
                adapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
                    @Override
                    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                        if (device.getAddress().equals(filterAddr)) {
                            //make available to whole module
                            Log.i(TAG,"Found the right one");
                            gatt = device.connectGatt(ControlScreenActivity.this, autoConnectBoolean, gattCallback);
                        }
                    }
                });


//
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
//        Intent Start_History = new Intent(this, xyz.efritz.bikecurious.RideHistoryActivity.class);
//        startActivity(Start_History);
        //code to send something
        String data = "fuck you";
        Log.i(TAG,data);
        tx.setValue(data);
        writingFlag = true;
        gatt.writeCharacteristic(tx);
    }

    public void click_logout(View view) {
        Intent login = new Intent(this, xyz.efritz.bikecurious.LoginActivity.class);
        startActivity(login);
        finish();
    }
}

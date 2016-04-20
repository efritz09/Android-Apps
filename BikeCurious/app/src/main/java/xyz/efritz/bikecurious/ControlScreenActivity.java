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
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    public String userAddr;
    Boolean autoConnectBoolean = true;
    Boolean writingFlag = false;
    Boolean locked = true;
    BluetoothGattCallback gattCallback;
    BluetoothAdapter.LeScanCallback callback;
    BluetoothGatt gatt;
    BluetoothGattCharacteristic tx;
    BluetoothGattCharacteristic rx;
    public static UUID TX_UUID = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID UART_UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID CLIENT_UUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E");

    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_screen);
        disableBluetoothControl();

        Switch mode, state;
        mode = (Switch) findViewById(R.id.lightMode);
        mode.setChecked(true);
        mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            TextView mode_switch = (TextView) findViewById(R.id.mode_text);

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mode_switch.setText(getString(R.string.modeAuto));
                    String data = "A";
                    Log.i(TAG,data);
                    tx.setValue(data);
                    writingFlag = true;
                    gatt.writeCharacteristic(tx);
                } else {
                    mode_switch.setText(getString(R.string.modeOn));
                    String data = "O";
                    Log.i(TAG,data);
                    tx.setValue(data);
                    writingFlag = true;
                    gatt.writeCharacteristic(tx);
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
                    String data = "S";
                    Log.i(TAG,data);
                    tx.setValue(data);
                    writingFlag = true;
                    gatt.writeCharacteristic(tx);
                } else {
                    state_switch.setText(getString(R.string.stateBlinking));
                    String data = "B";
                    Log.i(TAG,data);
                    tx.setValue(data);
                    writingFlag = true;
                    gatt.writeCharacteristic(tx);
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
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        Log.i(TAG, "gatt was successful");
                        enableBluetoothControl(); //enable the widgets
                        if (!gatt.discoverServices()) {
                            Log.i(TAG, "gatt not discovered");
                            //connectFailure();
                        }
                    } else {
                        Log.i(TAG,"gatt was unsuccessful");
                    }
                }
                else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                    Log.i(TAG, "clean up");
                    disableBluetoothControl(); //disable the widgets

                    //-------------------------------------------------------------------------------------------
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });
                    //-------------------------------------------------------------------------------------------

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
                Log.i(TAG,"discovered...");
                //send a "connected" data pack
                //This sends multiple times but I don't know how to fix it
                String data = "C";
                Log.i(TAG,data);
                tx.setValue(data);
                writingFlag = true;
                gatt.writeCharacteristic(tx);
                locked = false;
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
                if(locked) disconnect();
//                else Log.i(TAG,"some major fuckup");
                writingFlag = false;
            }


            public void disconnect() {
                if(gatt != null) {
                    gatt.disconnect();
                }
                gatt = null;
                tx = null;
                rx = null;
//                Toast.makeText(getApplicationContext(),"DISCONNECTED",Toast.LENGTH_SHORT).show();
            }
        };
    }



    /*
    disableBluetoothControl() -- used when it is not connected
    This sets the switches to disabled, changes the colors of the texts, and
    changes the lock/unlock button to display "unlock"
     */
    public void disableBluetoothControl() {
        Switch mode, state;
        mode = (Switch) findViewById(R.id.lightMode);
        state = (Switch) findViewById(R.id.lightState);
        mode.setClickable(false);
        state.setClickable(false);
        final Button lockButton = (Button)findViewById(R.id.lock_button);
        final TextView modeTitle, stateTitle, modeText, stateText, lockStatus, id, connect;
        lockStatus = (TextView)findViewById(R.id.lock_status);
        id = (TextView)findViewById(R.id.unique_identifier);
        connect = (TextView) findViewById(R.id.state_of_connection);
        modeTitle = (TextView)findViewById(R.id.mode_title);
        stateTitle = (TextView)findViewById(R.id.state_title);
        modeText = (TextView)findViewById(R.id.mode_text);
        stateText = (TextView)findViewById(R.id.state_text);
        //No idea what this does, but it was needed to make it work
        Handler refresh = new Handler(Looper.getMainLooper());
        refresh.post(new Runnable() {
            @Override
            public void run() {
                lockButton.setText(R.string.button_bluetooth_unlock);
                lockButton.setBackgroundColor(ContextCompat.getColor(ControlScreenActivity.this, R.color.green));
                lockStatus.setText(getString(R.string.locked));
                connect.setText(getString(R.string.disconnected));
                id.setText("");
                lockStatus.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.red)));
                modeTitle.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.lessdark)));
                stateTitle.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.lessdark)));
                modeText.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.lessdark)));
                stateText.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.lessdark)));
                connect.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.grey)));
            }
        });

    }

    /*
    enableBluetoothControl() -- used when it is connected
    This sets the switches to clickable, changes the colors of the texts, and
    changes the lock/unlock button to display "lock"
     */
    public void enableBluetoothControl() {
        Switch mode, state;
        mode = (Switch) findViewById(R.id.lightMode);
        state = (Switch) findViewById(R.id.lightState);
        mode.setClickable(true);
        state.setClickable(true);
        final Button lockButton = (Button)findViewById(R.id.lock_button);
        final TextView modeTitle, stateTitle, modeText, stateText, lockStatus, id, connect;
        lockStatus = (TextView)findViewById(R.id.lock_status);
        id = (TextView)findViewById(R.id.unique_identifier);
        connect = (TextView) findViewById(R.id.state_of_connection);
        modeTitle = (TextView)findViewById(R.id.mode_title);
        stateTitle = (TextView)findViewById(R.id.state_title);
        modeText = (TextView)findViewById(R.id.mode_text);
        stateText = (TextView)findViewById(R.id.state_text);
        //No idea what this does, but it was needed to make it work
        Handler refresh = new Handler(Looper.getMainLooper());
        refresh.post(new Runnable() {
            @Override
            public void run() {
                lockButton.setText(R.string.button_bluetooth_lock);
                lockButton.setBackgroundColor(ContextCompat.getColor(ControlScreenActivity.this, R.color.stanford));
                lockStatus.setText(getString(R.string.unlocked));
                connect.setText(getString(R.string.connected));
                id.setText(userAddr);
                lockStatus.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.green)));
                modeTitle.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.grey)));
                stateTitle.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.grey)));
                modeText.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.grey)));
                stateText.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.grey)));
                connect.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.grey)));
            }
        });

    }

    public void unlock(View view) {
        if(!locked) {
            Toast.makeText(getApplicationContext(),"locking",Toast.LENGTH_SHORT).show();
            //send a disconnect packet
            String data = "D";
            Log.i(TAG, data);
            tx.setValue(data);
            locked = true;
            writingFlag = true;
            gatt.writeCharacteristic(tx);
            return;
        }
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
                    userAddr = bikeID.getText().toString();
                    Toast.makeText(cont, getString(R.string.toast_addr_entered) + bikeID.getText().toString(), Toast.LENGTH_LONG).show();
                    //connect.setText(getString(R.string.connected)); //getString(R.string.name)
                    //id.setText(" " + bikeID.getText().toString());
                    //lockstat.setText(getString(R.string.unlocked));
                    //lockstat.setTextColor(ContextCompat.getColor(cont, R.color.red));
                }
                //--------------------------//





                //starts the scan
                final BluetoothAdapter.LeScanCallback scanCallback = new BluetoothAdapter.LeScanCallback() {
                    @Override
                    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                        Log.i(TAG,"SEARCHING..." + device.getAddress());

                        if (device.getAddress().equals(filterAddr)) {
                            adapter.stopLeScan(this);
                            //make available to whole module
//                            Toast.makeText(getApplicationContext(),"Searching",Toast.LENGTH_SHORT).show();
                            Log.i(TAG,"Found the right one");
                            final TextView connect = (TextView) findViewById(R.id.state_of_connection);
                            //update colors n shit
                            Handler refresh = new Handler(Looper.getMainLooper());
                            refresh.post(new Runnable() {
                                @Override
                                public void run() {
                                    connect.setText(getString(R.string.connecting));
                                    connect.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.gold)));
                                }
                            });
                            gatt = device.connectGatt(ControlScreenActivity.this, autoConnectBoolean, gattCallback);
                        }
                    }
                };
                adapter.startLeScan(scanCallback);
//                adapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
//                    @Override
//                    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//                        Log.i(TAG,"SEARCHING..." + device.getAddress());
//
//                        if (device.getAddress().equals(filterAddr)) {
//                            //make available to whole module
////                            Toast.makeText(getApplicationContext(),"Searching",Toast.LENGTH_SHORT).show();
//                            Log.i(TAG,"Found the right one");
//                            final TextView connect = (TextView) findViewById(R.id.state_of_connection);
//                            Handler refresh = new Handler(Looper.getMainLooper());
//                            refresh.post(new Runnable() {
//                                 @Override
//                                 public void run() {
//                                     connect.setText(getString(R.string.connecting));
//                                     connect.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(ControlScreenActivity.this, R.color.gold)));
//                                 }
//                             });
//                            gatt = device.connectGatt(ControlScreenActivity.this, autoConnectBoolean, gattCallback);
//                        }
//                    }
//                });


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
        Intent Start_History = new Intent(this, xyz.efritz.bikecurious.RideHistoryActivity.class);
        startActivity(Start_History);

    }

    public void click_logout(View view) {
        Intent login = new Intent(this, xyz.efritz.bikecurious.LoginActivity.class);
        startActivity(login);
        finish();
    }
}

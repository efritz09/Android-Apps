package xyz.efritz.bikecurious;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    String TAG = "LoginActivity";
    static HashMap<String,String> users = new HashMap<String,String>();
    Firebase ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://shining-heat-649.firebaseio.com/");
        users.put("efritz09@gmail.com", "testicles"); //debugging override

    }

    /*
    login_press
    checks the username and password. Launches controlScreenActivity if successful
     */
    public void login_press(View view) {
        EditText e_username = (EditText)findViewById(R.id.username_text);
        EditText e_password = (EditText)findViewById(R.id.password_text);
//        Context context = getApplicationContext();
//        String test = users.get(e_username.getText().toString());
//        String pass = e_password.getText().toString();

        ref.authWithPassword(e_username.getText().toString(),e_password.getText().toString(),
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Log.i(TAG,"authenticated");
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("provider", authData.getProvider());
                        map.put("more","more");
                        ref.child("users").child(authData.getUid()).setValue(map);
                        Intent Successful_login = new Intent(LoginActivity.this, xyz.efritz.bikecurious.ControlScreenActivity.class);
                        startActivity(Successful_login);
                        finish();
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        Log.i(TAG,"authentication error");
                        Toast.makeText(LoginActivity.this, getString(R.string.toast_failedlogin), Toast.LENGTH_SHORT).show();
                    }
                });



//        if(pass.equals(test)) {
//            Intent Successful_login = new Intent(this, xyz.efritz.bikecurious.ControlScreenActivity.class);
//            startActivity(Successful_login);
//            finish();
//        }else {
//            Toast.makeText(context, getString(R.string.toast_failedlogin), Toast.LENGTH_SHORT).show();
//        }

    }

    /*
    register_press
    let's the user register a new username and password. Adds it to the hashmap
     */
    public void register_press(View view) {

        //open display
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.register_dialog, null);
        builder.setView(dialogView);
        builder.setTitle(R.string.register_dialog_title);
        final EditText username = (EditText) dialogView.findViewById(R.id.register_username);
        final EditText password = (EditText) dialogView.findViewById(R.id.register_password);
        final EditText confirmp = (EditText) dialogView.findViewById(R.id.register_confrim_password);
        username.setTypeface(Typeface.DEFAULT);
        password.setTypeface(Typeface.DEFAULT);
        confirmp.setTypeface(Typeface.DEFAULT);
        builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_username = username.getText().toString();
                String user_password = password.getText().toString();
                String confirm_passw = confirmp.getText().toString();
                //check if username already exists
                if(users.containsKey(user_username)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_username_taken), Toast.LENGTH_SHORT).show();
                }else if(confirm_passw.equals(user_password)) {
                    //add it to the list
                    users.put(user_username, user_password);
                    //Map<String, Object> newUser = new HashMap<String, Object>();
//                    User newUser = new User(user_username,user_password);
                    ref.createUser(user_username, user_password,
                            new Firebase.ValueResultHandler<Map<String, Object>>() {
                                @Override
                                public void onSuccess(Map<String, Object> stringObjectHashMap) {
                                    Log.i(TAG, "Successfully added a user");
                                }

                                @Override
                                public void onError(FirebaseError firebaseError) {
                                    Log.i(TAG, "Failed to add user");
                                }
                            });
//                    ref.child("allUsers").push().setValue(newUser);
                    dialog.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_password_mismatch), Toast.LENGTH_SHORT).show();
                    password.setText(getString(R.string.empty));
                    confirmp.setText(getString(R.string.empty));
                }

                //FIREBASE


                //this throws an error somehow

            }
        });
    }
}




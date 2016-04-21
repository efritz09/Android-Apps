package xyz.efritz.bikecurious;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    static HashMap<String,String> users = new HashMap<String,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        users.put("t","p"); //debugging override
    }

    /*
    login_press
    checks the username and password. Launches controlScreenActivity if successful
     */
    public void login_press(View view) {
        EditText e_username = (EditText)findViewById(R.id.username_text);
        EditText e_password = (EditText)findViewById(R.id.password_text);
        Context context = getApplicationContext();
        String test = users.get(e_username.getText().toString());
        String pass = e_password.getText().toString();
        if(pass.equals(test)) {
            Intent Successful_login = new Intent(this, xyz.efritz.bikecurious.ControlScreenActivity.class);
            startActivity(Successful_login);
            finish();
        }else {
            Toast.makeText(context, getString(R.string.toast_failedlogin), Toast.LENGTH_SHORT).show();
        }

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
        builder.setTitle("Enter a username and password");
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
                    users.put(user_username,user_password);
                    dialog.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_password_mismatch), Toast.LENGTH_SHORT).show();
                    password.setText(getString(R.string.empty));
                    confirmp.setText(getString(R.string.empty));
                }
            }
        });
    }
}




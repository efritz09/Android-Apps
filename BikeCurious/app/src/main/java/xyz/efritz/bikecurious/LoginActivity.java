package xyz.efritz.bikecurious;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    static HashMap<String,String> users = new HashMap<String,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        users.put("test","pass");
    }
    String testcode = "";
    public void login_press(View view) {
        EditText e_username = (EditText)findViewById(R.id.username_text);
        EditText e_password = (EditText)findViewById(R.id.password_text);
        Context context = getApplicationContext();
        if( e_username.getText().toString().equals(testcode) && e_password.getText().toString().equals(testcode)) {
            Intent Successful_login = new Intent(this, xyz.efritz.bikecurious.ControlScreenActivity.class);
            startActivity(Successful_login);
            finish();
        }else {
            Toast.makeText(context, getString(R.string.toast_failedlogin), Toast.LENGTH_SHORT).show();
        }
    }

    public void register_press(View view) {
        //open display
        final Context context = getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.register_dialog, null);
        builder.setView(dialogView);
        builder.setTitle("Enter a username and password");

        final EditText username = (EditText) dialogView.findViewById(R.id.register_username);
        final EditText password = (EditText) dialogView.findViewById(R.id.register_password);
        final EditText confirmp = (EditText) dialogView.findViewById(R.id.register_confrim_password);



        builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //do something with edt.getText().toString();
                String user_username = username.getText().toString();
                String user_password = password.getText().toString();
                String confirm_passw = confirmp.getText().toString();
                //check if username already exists
                if(users.containsKey(user_username)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_username_taken), Toast.LENGTH_SHORT).show();
                }else if(confirm_passw.equals(user_password)) {
                    //add it to the list
                    users.put(user_username,user_password);
                }else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_password_mismatch), Toast.LENGTH_SHORT).show();
                    password.setText(getString(R.string.empty));
                    confirmp.setText(getString(R.string.empty));
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //pass
            }
        });
        AlertDialog b = builder.create();
        b.show();

        /*        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.register_dialog);
        dialog.setTitle("Enter a username and password");

        EditText username = (EditText) dialog.findViewById(R.id.register_username);
        EditText password = (EditText) dialog.findViewById(R.id.register_password);
        EditText confirmp = (EditText) dialog.findViewById(R.id.register_confrim_password);

        String user_password = password.getText().toString();
        String confirm_passw = confirmp.getText().toString();
        if(confirm_passw.equals(user_password)) {
            //add it to the list
        }else {
            Toast.makeText(context,getString(R.string.toast_password_mismatch),Toast.LENGTH_SHORT).show();
            password.setText(getString(R.string.empty));
            confirmp.setText(getString(R.string.empty));
        }*/


//
//        final EditText User = new EditText(getApplicationContext());
//        bikeID.setTextColor(ContextCompat.getColor(this, R.color.black));
//        bikeID.setHintTextColor(ContextCompat.getColor(this,R.color.grey));
//        //Use this if version < 23 (maybe add an if statement later?)
////        bikeID.setTextColor(this.getResources().getColor(R.color.black));
////        bikeID.setHintTextColor(this.getResources().getColor(R.color.grey));
//
//        bikeID.setHint(getString(R.string.hint_bikeaddr));
//        builder.setMessage(getString(R.string.unlockmsg));
//        builder.setTitle(getString(R.string.unlockquestion));
//        builder.setView(bikeID);
//        builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
//            Context cont = getApplicationContext();
//            TextView connect = (TextView) findViewById(R.id.state_of_connection);
//            TextView id = (TextView) findViewById(R.id.unique_identifier);
//            TextView lockstat = (TextView) findViewById(R.id.lock_status);
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (bikeID.getText().toString().equals("")) {
//                    Toast.makeText(cont, getString(R.string.toast_addr_empty), Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(cont, getString(R.string.toast_addr_entered) + bikeID.getText().toString(), Toast.LENGTH_LONG).show();
//                    connect.setText(getString(R.string.connected)); //getString(R.string.name)
//                    id.setText(bikeID.getText().toString());
//                    lockstat.setText(getString(R.string.unlocked));
//                    lockstat.setTextColor(ContextCompat.getColor(cont, R.color.red));
//                }
//
//            }
//        });
//        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
    }
}




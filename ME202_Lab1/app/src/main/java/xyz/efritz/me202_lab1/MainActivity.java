package xyz.efritz.me202_lab1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    String testcode = "";

    public void login_press(View view) {
        EditText e_username = (EditText)findViewById(R.id.username_text);
        EditText e_password = (EditText)findViewById(R.id.password_text);
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        if( e_username.getText().toString().equals(testcode) && e_password.getText().toString().equals(testcode)) {
//            CharSequence text = "Login Succeeded";
//            Toast login_success = Toast.makeText(context, text, duration);
//            login_success.show();

            Intent Successful_login = new Intent(this, ControlScreen.class);
            startActivity(Successful_login);

        }else {
//            CharSequence text = e_username.getText().toString() + e_password.getText().toString();
            CharSequence text = "Login Failed";
            Toast login_failed = Toast.makeText(context, text, duration);
            login_failed.show();
        }
    }

}

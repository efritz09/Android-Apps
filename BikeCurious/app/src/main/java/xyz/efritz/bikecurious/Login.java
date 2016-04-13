package xyz.efritz.bikecurious;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    String testcode = "";
    public void login_press(View view) {
        EditText e_username = (EditText)findViewById(R.id.username_text);
        EditText e_password = (EditText)findViewById(R.id.password_text);
        Context context = getApplicationContext();
        if( e_username.getText().toString().equals(testcode) && e_password.getText().toString().equals(testcode)) {
            Intent Successful_login = new Intent(this, xyz.efritz.bikecurious.ControlScreen.class);
            startActivity(Successful_login);
            finish();
        }else {
            Toast.makeText(context, getString(R.string.toast_failedlogin), Toast.LENGTH_SHORT).show();
        }
    }
}

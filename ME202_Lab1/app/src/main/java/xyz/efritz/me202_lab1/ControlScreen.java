package xyz.efritz.me202_lab1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ControlScreen extends AppCompatActivity {

    Context context = getApplicationContext();
    int duration = Toast.LENGTH_SHORT;
    CharSequence text = "empty";

//    Switch mode, state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_screen);


        Toast.makeText(context,"shitbox",duration).show();




//        mode = (Switch)findViewById(R.id.lightMode);
//        state = (Switch)findViewById(R.id.lightState);
//
//        mode.setChecked(true);
//        state.setChecked(true);
//
//        mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    Toast.makeText(context, "mode switch on", duration).show();
//                } else {
//                    Toast.makeText(context, "mode switch off", duration).show();
//                }
//            }
//        });
//
//        if(mode.isChecked()) {
//            Toast.makeText(context, "mode switch on", duration).show();
//        } else {
//            Toast.makeText(context, "mode switch off", duration).show();
//        }
//
//
//        state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked) {
//                    Toast.makeText(context, "state switch on", duration).show();
//                } else {
//                    Toast.makeText(context, "state switch off", duration).show();
//                }
//            }
//        });
//        if(state.isChecked()) {
//            Toast.makeText(context, "mode switch on", duration).show();
//        } else {
//            Toast.makeText(context, "mode switch off", duration).show();
//        }

    }

//    public void unlock(View view) {
//        //open display
//        Toast.makeText(context, "fuck off",duration).show();
//    }
//
//    public void rider_history(View view) {
//        Toast.makeText(context, "eat cock",duration).show();
//    }

    //check the light mode switch


}

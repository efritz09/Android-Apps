package xyz.efritz.bikecurious;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class RideHistory extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_history);

        ImageView image = (ImageView)findViewById(R.id.history_background_image);
        Picasso.with(this)
                .load(R.drawable.img_history_background)
                .fit()
                .centerCrop()
                .into(image);
    }
}

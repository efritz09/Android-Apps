package xyz.efritz.bikecurious;

import com.firebase.client.Firebase;

/**
 * Created by Eric on 4/23/2016.
 */
public class BikeCurious extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}

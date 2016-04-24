package xyz.efritz.bikecurious;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Eric on 4/20/2016.
 */
public class HistoryDatabaseAdapter {
    static final String TAG = "HistoryDatabaseAdapter";
    static final String DATABASE_NAME = "history.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    static final String DATABASE_CREATE = "create table " + "HISTORY" + "( "
            + "ID" + " integer primary key autoincrement,"
            + "LOCATION  text, DATE text, IMAGEID int); ";
    public SQLiteDatabase db;
    private final Context context;
    private DatabaseHelper dbHelper;

    public HistoryDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    public HistoryDatabaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String location, String date, int imageID) {

        ContentValues newValues = new ContentValues();
        newValues.put("LOCATION", location);
        newValues.put("DATE", date);
        newValues.put("IMAGEID", imageID);

        // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
        db.insert("HISTORY", null, newValues);
    }

    public int deleteEntry(String date) {

        String where = "DATE=?";
        int numberOFEntriesDeleted = db.delete("HISTORY", where,
                new String[] { date });
        return numberOFEntriesDeleted;
    }

    public Ride getSingleEntry(String date) {
        Cursor cursor = db.query("HISTORY", null, " DATE=?",
                new String[] { date }, null, null, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return null;
        }
        Ride ride = new Ride();
        cursor.moveToFirst();
        ride.location = cursor.getString(cursor.getColumnIndex("LOCATION"));
        ride.date = cursor.getString(cursor.getColumnIndex("DATE"));
        ride.imageID = cursor.getInt(cursor.getColumnIndex("IMAGEID"));
        cursor.close();
        return ride;
    }

    public ArrayList<Ride> getAllEntries() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + "HISTORY", null);
        cursor.moveToFirst();
        ArrayList<Ride> rides = new ArrayList<>();
        while (cursor.moveToNext()) {
            // Extract data.
            Ride ride = new Ride();
            ride.location = cursor.getString(cursor.getColumnIndex("LOCATION"));
            ride.date = cursor.getString(cursor.getColumnIndex("DATE"));
            ride.imageID = cursor.getInt(cursor.getColumnIndex("IMAGEID"));
            rides.add(0,ride);
        }
        cursor.close();
        return rides;
    }

    public boolean isEmpty() {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + "HISTORY", null);
        if (mCursor.moveToFirst()) {
            Log.i(TAG,"not empty");
            return false;
        }else {
            Log.i(TAG,"empty");
            return true;
        }
    }


    public void updateEntry(String location, String date, int imageID) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("LOCATION", location);
        updatedValues.put("DATE", date);
        updatedValues.put("IMAGEID",imageID);

        String where = "DATE = ?";
        db.update("HISTORY", updatedValues, where, new String[] { date });
    }
}

package sg.edu.np.WhackAMole;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USERNAME = "USER_NAME";
    public static final String COLUMN_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_LEVEL = "USER_LEVEL";
    public static final String COLUMN_SCORE = "USER_SCORE";
    public static final String COLUMN_ID = "USER_ID";
    String TAG = "Whack-A-Mole3.0!";

    public MyDBHandler(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    // this is called the first time a database is accessed. Creation a new database will involve here
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_LEVEL + " INTEGER, " + COLUMN_SCORE + " INTEGER)";
        Log.v(TAG, "Created Table");
        db.execSQL(createTableStatement);
    }

    //this is called if database version number changes. It prevents previous users apps from breaker when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Used for registration
    boolean addData(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        int loop = 1;
        ContentValues cv = new ContentValues();
        do {
            cv.put(COLUMN_USERNAME, user.getUsername());
            cv.put(COLUMN_PASSWORD, user.getPassword());
            cv.put(COLUMN_LEVEL, loop);
            cv.put(COLUMN_SCORE, 0);
            loop += 1;
            db.insert(USER_TABLE, null, cv); //if insert is -1 means fail
        } while (loop != 11);

        Log.v(TAG, "Adding data for Database" + cv.toString());

        return true;
    }

    //Authentication of user username and password

    public User verifyUser(String Username, String Password) {
        Log.v(TAG, "New user creation with " + Username + ":" + Password);

        User newFocus = new User();
        String queryString = "Select * FROM " + USER_TABLE + " WHERE " + COLUMN_USERNAME + " = \"" + Username + "\"" + " AND " + COLUMN_PASSWORD + " = \"" + Password + "\"";
        Log.v(TAG, "Verifying User for " + Username + ":" + Password);
        Log.v(TAG, queryString);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);


        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            newFocus = null;
            Log.v(TAG, "User Not Found");
        } else {
            Log.v(TAG, "Found User, Adding result");
            String username = cursor.getString(1); //fbId
            newFocus.setUsername(username);
            do {
                Log.v(TAG, "Adding");
                int level = cursor.getInt(3);
                int score = cursor.getInt(4);
                newFocus.addScore(score, level);
                Log.v(TAG, "Found User");
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return newFocus;
    }

    //This is to check if user exist in database
    public boolean checkExistUser(String Username) {

        boolean exist;
        String queryString = "Select * FROM " + USER_TABLE + " WHERE " + COLUMN_USERNAME + " = \"" + Username + "\"";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            exist = false;
        } else {
            exist = true;
        }
        cursor.close();
        db.close();

        return exist;
    }

    //Used to look for score
    //This is to check if user exist in database
    public void updateScore(String Username, int level, int score) {

        String queryString = "UPDATE " + USER_TABLE + " SET " + COLUMN_SCORE + " = \"" + score + "\"" + " WHERE " + COLUMN_USERNAME + " = \"" + Username + "\"" + " AND " + COLUMN_LEVEL + " = \"" + level + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(queryString);
        db.close();
        Log.v(TAG, "Updated level " + level + " Score to " + score);
    }

    //Get User List of highscore
    public ArrayList<Integer> getAllScore(String Username) {

        ArrayList<Integer> scoreList = new ArrayList<>();
        String queryString = "Select * FROM " + USER_TABLE + " WHERE " + COLUMN_USERNAME + " = \"" + Username + "\"" + "";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);


        Log.v(TAG, "Getting scores for " + queryString);


        if(cursor.moveToFirst()) {
            do {
                int score = cursor.getInt(4);
                scoreList.add(score);
            } while (cursor.moveToNext());


        }else{
            Log.v(TAG, "GetScore Database Failed");
        }

        cursor.close();
        db.close();
        return scoreList;
    }

    public int getLevelScore(String Username, String Level) {

        int getScore = 0;

        String queryString = "Select * FROM " + USER_TABLE + " WHERE " + COLUMN_USERNAME + " = \"" + Username + "\"" + " AND " + COLUMN_LEVEL + " = \"" + Level + "\"";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);


        Log.v(TAG, "Getting scores for " + queryString);


        if(cursor.moveToFirst()) {
            do {
                getScore = cursor.getInt(4);
            } while (cursor.moveToNext());


        }else{
            Log.v(TAG, "Get Level Database Failed");
        }

        cursor.close();
        db.close();
        return getScore;
    }

    public boolean deleteAccount(String username) {
        /* HINT:
            This finds and delete the user data in the database.
            This is not reversible.
            Log.v(TAG, FILENAME + ": Database delete user: " + query);
         */
        return false;
    }

}

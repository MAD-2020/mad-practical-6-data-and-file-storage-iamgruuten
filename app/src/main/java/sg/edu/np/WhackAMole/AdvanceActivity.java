package sg.edu.np.WhackAMole;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

public class AdvanceActivity extends AppCompatActivity {

    String TAG = "Advance";
    private CountDownTimer mCountDownTimer;
    int advancedScore = 0;
    int level;
    String USERNAME = "";

    TextView viewScore;
    String seconds;
    long timeleft, moleTimes;
    Toast toast = null;

    private void readyTimer() {
        timeleft = 10 * 1000;
        mCountDownTimer = new CountDownTimer(timeleft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (toast != null) toast.cancel();
                Log.v(TAG, "OnTick: " + millisUntilFinished);
                if (toast != null) toast.cancel();
                seconds = String.valueOf(millisUntilFinished / 1000);
                toast = Toast.makeText(AdvanceActivity.this, "Get Ready in " + seconds + " Seconds", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFinish() {
                toast.cancel();
                Log.v(TAG, String.valueOf(moleTimes));
                placeMoleTimer(moleTimes);
                Log.v(TAG, "Completed");
                Toast.makeText(AdvanceActivity.this, "Go", Toast.LENGTH_SHORT).show();

            }
        }.start();
    }

    private void placeMoleTimer(long s) {
        int newPost = setNewMole();
        //Check what level is this
        if(level > 5){
            set2ndMole(newPost);
        }

        mCountDownTimer = new CountDownTimer(s, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                int newPost = setNewMole();
                if(level > 5){
                    set2ndMole(newPost);
                }
                mCountDownTimer.start();
            }
        }.start();
    }

    @Override
    public void onBackPressed() {

        //Used to update score
        Intent intent = new Intent();
        intent.putExtra("username", USERNAME);
        intent.putExtra("level", level);
        intent.putExtra("score", advancedScore);

        setResult(Activity.RESULT_OK, intent);

        super.onBackPressed();

        mCountDownTimer.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AdvanceActivity.this.finish();
    }

    private static final int[] BUTTON_IDS = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private ArrayList<Button> buttonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        advancedScore = 0;


        Log.v(TAG, "Current User Score: " + advancedScore);
        viewScore = findViewById(R.id.txtscore);
        viewScore.setText("0");

        //getting data that is stored in Intent
        moleTimes = getIntent().getExtras().getLong("timer");
        USERNAME = getIntent().getExtras().getString("username");
        level = getIntent().getExtras().getInt("level");

        readyTimer();

        for (int i = 1; i <= BUTTON_IDS.length; i++) {
            final Button button = findViewById(getResources().getIdentifier("button" + i, "id", this.getPackageName()));
            buttonList.add(button);
        }

        //Set on click listener to object are button id in the array
        for (int i = 0; i < buttonList.size(); i++) {
            Button button = buttonList.get(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    String buttonText = b.getText().toString();
                    doCheck(buttonText, b.getId());
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Set all to "O"
        for (int i = 1; i <= BUTTON_IDS.length; i++) {
            Button button = (Button) findViewById(getResources().getIdentifier("button" + i, "id", this.getPackageName()));
            button.setText("O");
        }
    }

    private void doCheck(String checkButton, int position) {
        if (checkButton == "*") {
            Log.v(TAG, "Hit, Score added!");
            advancedScore = advancedScore + 1;
            clearHitMole(position);
        } else {
            Log.v(TAG, "Missed, Score deducted");
            advancedScore = advancedScore - 1;
        }
        viewScore.setText(Integer.toString(advancedScore));
    }

    public void clearHitMole(int position) {
        Button button = findViewById(position);
        button.setText("O");
    }

    //Change mole position
    public int setNewMole() {
        //Set all to "O"
        for (int i = 1; i <= BUTTON_IDS.length; i++) {
            Button button = (Button) findViewById(getResources().getIdentifier("button" + i, "id", this.getPackageName()));
            button.setText("O");
        }

        Random ran = new Random();
        int randomLocation = ran.nextInt(8) + 1;
        Log.v(TAG, "Its located at: " + randomLocation);

        Button button = (Button) findViewById(getResources().getIdentifier("button" + randomLocation, "id", this.getPackageName()));
        button.setText("*");
        return randomLocation;
    }

    //A given challenge, to appear two mole at two different position
    public void set2ndMole(int moleLocate) {
        Random ran = new Random();
        int x = ran.nextInt(8) + 1;
        while (x == moleLocate) {
            x = ran.nextInt(8) + 1;
        }

        Log.v(TAG, "2nd mole located at: " + ran);

        Button button = (Button) findViewById(getResources().getIdentifier("button" + x, "id", this.getPackageName()));
        button.setText("*");
    }
}


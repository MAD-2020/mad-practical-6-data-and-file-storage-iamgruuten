package sg.edu.np.WhackAMole;

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
    int highScore = 0;
    int level;
    String USERNAME = "";

    TextView viewScore;
    String seconds;
    long timeleft, moleTimes;
    Toast toast = null;
    String textButton;
    MyDBHandler myDBHandler;


    private void readyTimer( ){
       timeleft = 10 * 1000;
       mCountDownTimer = new CountDownTimer(timeleft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(toast != null) toast.cancel();
                Log.v(TAG,"OnTick: " + millisUntilFinished);
                if(toast != null) toast.cancel();
                seconds = String.valueOf(millisUntilFinished / 1000);
                toast = Toast.makeText(AdvanceActivity.this, "Get Ready in " + seconds + " Seconds", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFinish() {
                toast.cancel();
                Log.v(TAG, String.valueOf(moleTimes));
                placeMoleTimer(moleTimes);
                Log.v(TAG,"Completed");
                Toast.makeText(AdvanceActivity.this, "Go", Toast.LENGTH_SHORT).show();

            }
        }.start();
    }
    private void placeMoleTimer(long s){
        setNewMole();

        mCountDownTimer = new CountDownTimer(s, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                setNewMole();
                mCountDownTimer.start();
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Used to update score
        updateHighScore(USERNAME, level, advancedScore);
        mCountDownTimer.cancel();
        AdvanceActivity.this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AdvanceActivity.this.finish();
    }

    private static final int[] BUTTON_IDS = {1,2,3,4,5,6,7,8,9};
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
        highScore = getIntent().getExtras().getInt("highscore");
        USERNAME = getIntent().getExtras().getString("username");
        level = getIntent().getExtras().getInt("level");
        //TODO Get Mole Timer with getIntent
        readyTimer();

        for (int i = 1; i <= BUTTON_IDS.length; i++) {
            final Button button = findViewById(getResources().getIdentifier("button" + i, "id", this.getPackageName()));
            buttonList.add(button);
        }

        for(int i = 0; i < buttonList.size(); i++){
            final Button button = buttonList.get(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    String buttonText = b.getText().toString();
                    doCheck(buttonText);
                }
            });
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        //Set all to "O"
        for (int i = 1; i <= BUTTON_IDS.length; i++) {
            Button button = (Button) findViewById(getResources().getIdentifier("button" + i, "id", this.getPackageName()));
            button.setText("O");
        }
    }

    private void doCheck(String checkButton)
    {
        if(checkButton == "*") {Log.v(TAG, "Hit, Score added!"); advancedScore = advancedScore + 1;}
        else{ Log.v(TAG, "Missed, Score deducted"); advancedScore = advancedScore - 1;}
        viewScore.setText(Integer.toString(advancedScore));
        setNewMole();
    }

    public void setNewMole()
    {
        //Set all to "O"
        for (int i = 1; i <= BUTTON_IDS.length; i++) {
            Button button = (Button) findViewById(getResources().getIdentifier("button" + i, "id", this.getPackageName()));
            button.setText("O");
        }

        Random ran = new Random();
        int randomLocation = ran.nextInt(8)+1;
        Log.v(TAG,"Its located at: " + randomLocation);

        Button button = (Button) findViewById(getResources().getIdentifier("button" + randomLocation, "id", this.getPackageName()));
        button.setText("*");
    }

    public void updateHighScore(String username, int level, int score){
        myDBHandler = new MyDBHandler(this);
        myDBHandler.updateScore(username, level, score);
    }
}


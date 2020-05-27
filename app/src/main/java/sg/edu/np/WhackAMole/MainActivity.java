package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button ButtonLeft;
    private Button ButtonRight;
    private Button ButtonMiddle;
    private TextView TextScore;

    private String Score = "0";
    private int intRandom;
    private static final String TAG = "ButtonActivity";
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG,"Whack-A-Mole" );
        ButtonLeft = findViewById(R.id.button1); //Set as 1
        ButtonMiddle = findViewById(R.id.ButtonMiddle); //Set as 2
        ButtonRight = findViewById(R.id.ButtonRight); //Set as 3
        TextScore = findViewById(R.id.TextViewScore); //Score view
        reset();

        ButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Button Left clicked!" );
                Score = check(1, intRandom, Score);
                TextScore.setText(Score);
                reset();
            }
        });

        ButtonMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Button Middle clicked!" );
                Score = check(2, intRandom, Score);
                TextScore.setText(Score);
                reset();
            }
        });

        ButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Button Right clicked!" );
                Score = check(3, intRandom, Score);
                TextScore.setText(Score);
                reset();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG,"Started");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG,"Paused");
    }

    private void reset(){
        //Initialize
        intRandom = random(); //Get Random
        if(intRandom == 1){ButtonLeft.setText("*"); ButtonMiddle.setText("O"); ButtonRight.setText("O");}
        else if(intRandom == 2){ButtonMiddle.setText("*"); ButtonLeft.setText("O"); ButtonRight.setText("O");}
        else if(intRandom == 3){ButtonRight.setText("*"); ButtonMiddle.setText("O"); ButtonLeft.setText("O");}
    }

    private int random(){
        return random.nextInt(3) + 1;
    }

    private String check(int ButtonClicked, int value, String score){
        int Score =  Integer.parseInt(score);
        if(ButtonClicked == value){
            Log.v(TAG, "Hit, Score added!");
            Score = Score + 1;
        }else{
            Log.v(TAG, "Missed, Score deducted!");
            Score = Score - 1;
        }

        //Check qualify for advance stage

        if(Score == 10){
            nextLevelQuery();
        }

        return String.valueOf(Score);
    }


    private void nextLevelQuery(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning! Insane Whack-A-Mole Incoming!");
        builder.setMessage("Would you like to advance to advanced mode?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                Log.v(TAG,"User accepted");
                nextLevel();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                Log.v(TAG,"User decline");
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/
    }

    private void nextLevel(){
        Intent intent = new Intent(MainActivity.this, AdvanceActivity.class);
        intent.putExtra("Score", Score);
        startActivity(intent);
    }
}
package sg.edu.np.WhackAMole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectLevelActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String TAG = "WHACK-A-MOLE!";
    Adapter mAdapter;
    User user = new User();
    Button back;
    MyDBHandler myDBHandler;
    int getHighScore;
    int newScore;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.v(TAG, "Receive back result" + requestCode + " " + data);
        if (resultCode == Activity.RESULT_OK) {
            String username = data.getStringExtra("username");
            int level = data.getIntExtra("level", 0);
            int score = data.getIntExtra("score", 0);
            updateHighScore(username, level, score);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);

        recyclerView = findViewById(R.id.recyclerView);
        back = findViewById(R.id.btn_backlogin);

        myDBHandler = new MyDBHandler(this);
        Log.v(TAG, "Display level selection activity");

        //Getting data that was passed from login
        user.setUsername(getIntent().getExtras().getString("username"));
        user.setScores(getIntent().getExtras().getIntegerArrayList("scoreList"));
        user.setLevels(getIntent().getExtras().getIntegerArrayList("levelList"));

        Log.v(TAG, user.getScores().toString());
        setmAdapter();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void updateHighScore(String username, int level, int score){
        myDBHandler = new MyDBHandler(this);
        //First before update the high score, there is a need to check if current score is higher
        //This method will allow get the current high score of the level
        getHighScore = myDBHandler.getLevelScore(username, String.valueOf(score));

        //This is used to compare the current score to the high score
        if(getHighScore < score) {
            myDBHandler.updateScore(username, level, score);

            this.user.setScores(myDBHandler.getAllScore(user.getUsername()));
            setmAdapter();
            //TODO Update the data on recyclerview
            mAdapter.reloadData();
        }
    }

    public void setmAdapter(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new Adapter(user,SelectLevelActivity.this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}

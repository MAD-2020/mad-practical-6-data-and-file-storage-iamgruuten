package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class SelectLevelActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String TAG = "WHACK-A-MOLE!";
    Adapter mAdapter;
    User user = new User();
    Button back;
    MyDBHandler myDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);

        myDBHandler = new MyDBHandler(this);
        Log.v(TAG, "Display level selection activity");

        user.setUsername(getIntent().getExtras().getString("username"));
        user.setScores(getIntent().getExtras().getIntegerArrayList("scoreList"));
        user.setLevels(getIntent().getExtras().getIntegerArrayList("levelList"));

        Log.v(TAG, user.getScores().toString());

        recyclerView = findViewById(R.id.recyclerView);
        back = findViewById(R.id.btn_backlogin);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mAdapter = new Adapter(user,this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

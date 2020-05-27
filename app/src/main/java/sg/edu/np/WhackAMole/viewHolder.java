package sg.edu.np.WhackAMole;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class viewHolder extends RecyclerView.ViewHolder {
    TextView level, score;
    private Context mContext;
    int highScore, levelSelect;
    String username;

    Adapter adapter;
    long timer = 11;

    public viewHolder(View view, final Context context, final Adapter adapter){
        super(view);
        level = view.findViewById(R.id.txtLevel);
        score = view.findViewById(R.id.txtscore);
        this.adapter = adapter;
        this.mContext = context;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highScore = adapter.getHighScore(getLayoutPosition());
                levelSelect = adapter.getLevel(getLayoutPosition());
                Log.v("TAG", String.valueOf(levelSelect));
                username = adapter.getUsername();

                //This is to store the timer data for the mole time
                Intent intent = new Intent(mContext, AdvanceActivity.class);
                long moleTime = (timer - levelSelect) * 1000;

                intent.putExtra("timer", moleTime);
                intent.putExtra("highscore", highScore);
                intent.putExtra("level", levelSelect);
                intent.putExtra("username", username);
                Log.v("ViewHolder", String.valueOf(moleTime));
                mContext.startActivity(intent);
            }
        });
    }
    public viewHolder(@NonNull View itemView) {
        super(itemView);
    }

}

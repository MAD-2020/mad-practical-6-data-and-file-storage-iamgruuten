package sg.edu.np.WhackAMole;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<viewHolder> {
    private Context context;
    private User user = new User();
    int highscore, levelscore;

    public Adapter(User user, Context context){
        this.user.setScores(user.getScores());
        this.user.setLevels(user.getLevels());
        this.user.setUsername(user.getUsername());
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.activity_view_holder, viewGroup, false);
        return new viewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.score.setText("Highest Score: " + user.getScores().get(i).toString());
        viewHolder.level.setText("Level: " + user.getLevels().get(i).toString());

    }

    @Override
    public int getItemCount() {
        return user.getLevels().size();
    }

    public Integer getHighScore(int position) {
        return user.getScores().get(position);
    }

    public Integer getLevel (int position) {
        return user.getLevels().get(position);
    }

    public String getUsername(){
        return user.getUsername();
    }

    public void notify(int potion){
        notifyDataSetChanged();
    }
}

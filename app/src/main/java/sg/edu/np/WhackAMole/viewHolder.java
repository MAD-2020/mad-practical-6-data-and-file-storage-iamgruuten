package sg.edu.np.WhackAMole;

import android.app.Activity;
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
    int levelSelect;
    String username;

    Adapter adapter;

    public viewHolder(View view, final Context context, final Adapter adapter){
        super(view);
        level = view.findViewById(R.id.txtLevel);
        score = view.findViewById(R.id.txtscore);
        this.adapter = adapter;
        this.mContext = context;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               adapter.openActivity(getLayoutPosition());


            }
        });
    }


    public viewHolder(@NonNull View itemView) {
        super(itemView);
    }

}

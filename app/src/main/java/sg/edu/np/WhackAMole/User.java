package sg.edu.np.WhackAMole;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

public class User  {


    String username;
    String password;
    private ArrayList<Integer> Scores = new ArrayList<>();
    private ArrayList<Integer> Levels = new ArrayList<>();


    public User(String username, String password, ArrayList<Integer> scores, ArrayList<Integer> levels) {
        this.username = username;
        this.password = password;
        Scores = scores;
        Levels = levels;
    }

    public ArrayList<Integer> getScores() {
        return Scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        Scores = scores;
    }

    public ArrayList<Integer> getLevels() {
        return Levels;
    }

    public void setLevels(ArrayList<Integer> levels) {
        Levels = levels;
    }

    public User(){

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addScore(Integer score, Integer levels){
        Scores.add(score);
        Levels.add(levels);
        Log.v("User", "Initializing Score");
    }

    public void updateScore(Integer score, Integer levels){
        Scores.set(levels, score);
        Log.v("User", "Updated Score");
    }
}

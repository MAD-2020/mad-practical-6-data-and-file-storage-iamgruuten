package sg.edu.np.WhackAMole;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_username, et_password;
    Button btn_login;
    TextView txt_newUser;
    MyDBHandler myDBHandler;
    String username, password;
    String TAG = "Login";
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDBHandler = new MyDBHandler(this);
        et_username = findViewById(R.id.editUsername);
        et_password = findViewById(R.id.editPassword);
        btn_login = findViewById(R.id.btn_login);
        txt_newUser = findViewById(R.id.txtNewUser);

        et_username.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        et_password.setOnClickListener(this);
        txt_newUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtNewUser:
                Intent intent = new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                username = et_username.getText().toString();
                password = et_password.getText().toString();

                Log.v(TAG, "Logging in with: " + username);
                user = myDBHandler.verifyUser(username, password);

                if (user != null) {
                    Log.v(TAG, "Data matched. Login user now");
                    Intent intent1 = new Intent(this, SelectLevelActivity.class);
                    intent1.putExtra("scoreList", user.getScores());
                    Log.v(TAG, user.getScores().toString());
                    intent1.putExtra("levelList", user.getLevels());
                    Log.v(TAG, user.getLevels().toString());
                    intent1.putExtra("username", user.getUsername());
                    startActivity(intent1);
                } else {
                    Log.v(TAG, "No data found");
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.editUsername:
                ShowKeyboard(et_username);
                break;
            case R.id.editPassword:
                ShowKeyboard(et_password);
                break;
        }
    }

    //Soft Keyboard methods
    private void ShowKeyboard(EditText taskInput) {
        final InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(taskInput, 0);
        Log.i(TAG, "Show Keyboard");

    }


}

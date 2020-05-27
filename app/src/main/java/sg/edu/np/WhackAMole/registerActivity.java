package sg.edu.np.WhackAMole;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class registerActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et_username, et_password;
    Button btn_register, btn_cancel;
    MyDBHandler myDBHandler = new MyDBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_username = findViewById(R.id.registerUsername);
        et_password = findViewById(R.id.registerPassword);
        btn_register = findViewById(R.id.btn_register);
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_register.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_cancel:
                onBackPressed();
                break;
            case R.id.btn_register:
                String user = et_username.getText().toString();
                String pwd = et_password.getText().toString();
                RegisterUser(user, pwd);
                break;
        }
    }

    public void RegisterUser(String username, String Password){
        boolean existUser = myDBHandler.checkExistUser(username);
        if(existUser){
            Toast.makeText(this, "User Already Exist.\n Please Try Again", Toast.LENGTH_SHORT).show();
        }else{
            //Set number of level
            ArrayList<Integer> level = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
            ArrayList<Integer> score = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0));

            User user = new User(username, Password, score ,level);
            myDBHandler.addData(user);
            Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}

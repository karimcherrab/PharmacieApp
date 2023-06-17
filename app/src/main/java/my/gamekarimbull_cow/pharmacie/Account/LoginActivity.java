package my.gamekarimbull_cow.pharmacie.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import my.gamekarimbull_cow.pharmacie.HomeActivity;
import my.gamekarimbull_cow.pharmacie.MainActivity;
import my.gamekarimbull_cow.pharmacie.R;
import my.gamekarimbull_cow.pharmacie.models.User;

public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    EditText email_login , password_login;
    TextView toSignUp;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String uid;
    ProgressBar progressBar_Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        btn_login = findViewById(R.id.btn_login);
        toSignUp = findViewById(R.id.toSignUp);
        progressBar_Login = findViewById(R.id.progressBar_Login);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if (auth.getCurrentUser()!=null) {
            startActivity(new Intent(LoginActivity.this , HomeActivity.class));
            finish();
            
        }

        toSignUp.setOnClickListener(v -> {
                    startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                    finish();

                }

        );
        btn_login.setOnClickListener(v -> {
            if (email_login.getText().toString().equals("")) {
                emptyError(email_login, "empty");
            } else if (password_login.getText().toString().equals("")) {
                emptyError(password_login, "empty");
            } else {
                btn_login.setVisibility(View.GONE);
                progressBar_Login.setVisibility(View.VISIBLE);
                User user = new User();
                user.login(email_login.getText().toString(), password_login.getText().toString() , auth , LoginActivity.this,database);
            }
        });


    }



    private void emptyError(EditText editText , String error){
        editText.setError(error);
        editText.requestFocus();
    }
}
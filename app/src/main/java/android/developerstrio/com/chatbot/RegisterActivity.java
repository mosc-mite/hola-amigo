package android.developerstrio.com.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameET;
    private EditText ageET;
    private EditText heightET;
    private EditText weightET;
    private EditText emailET;
    private EditText passwordET;
    private Button Register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameET = findViewById(R.id.register_name);
        ageET=findViewById(R.id.register_age);
        heightET=findViewById(R.id.register_height);
        weightET=findViewById(R.id.register_weight);
        emailET=findViewById(R.id.register_email);
        passwordET = findViewById(R.id.register_password);
        Register = findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                String name = nameET.getText().toString();
                String age = ageET.getText().toString();
                String  height = ageET.getText().toString();
                String weight = ageET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                if(name.equals("")||age.equals("")||height.equals("")||weight.equals("")||email.equals("")||password.equals("")){
                  showMessage("enter all the details");
                }else{
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                showMessage("registration successful");
                                showMessage("go to login page");
                                finish();
                            }
                            else{
                                showMessage("error :" + task.getException().toString());
                            }
                        }
                    });


                }
            }
        });



    }

    private void updateUI() {
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void showMessage(String s) {
        Toast.makeText(RegisterActivity.this,s,Toast.LENGTH_SHORT).show();
    }
}

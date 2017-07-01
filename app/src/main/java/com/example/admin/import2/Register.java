package com.example.admin.import2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    //for authorization
    private FirebaseAuth auth;
    //for reading and writing to database
    private DatabaseReference mDatabase;

    private Button btn_submit;
    private EditText inputName, inputPhone;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

      /* if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Register.this, MainActivity.class));
            finish();
        }*/


        // Setting the view now
        setContentView(R.layout.activity_register);
        //Get Firebase database instant
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user=auth.getCurrentUser();
        userID = user.getUid();
        inputName=(EditText)findViewById(R.id.inputName);
        inputPhone=(EditText)findViewById(R.id.inputPhone);
        btn_submit=(Button)findViewById(R.id.btn_submit);



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String phone = inputPhone.getText().toString();
                //Handling exceptions
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }




                    User user = new User(name, phone);

                    mDatabase.child("users").child(userID).setValue(user);
                    Toast.makeText(getApplicationContext(),"Info Updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, MainActivity.class));
                     finish();



            }
        });
    }

    public class User {

        public String username;
        public String phone;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String phone) {
            this.username = username;
            this.phone = phone;
        }

    }

}

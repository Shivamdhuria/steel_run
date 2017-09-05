package com.elixer.reemind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.elixer.reemind.MainActivity.auth;
import static com.elixer.reemind.MainActivity.tinyDBM;
import static com.elixer.reemind.MainActivity.userID;

public class EditUserName extends AppCompatActivity {
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_user_name);
        button=(Button)findViewById(R.id.btn_Confirm);

        editText = (EditText) findViewById(R.id.displayName);
        editText.setText(tinyDBM.getString("userNameDisplay"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(editText.getText());
                tinyDBM.putString("userNameDisplay", name);

                DatabaseReference mDatabase ;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                FirebaseUser user = auth.getCurrentUser();
                userID = user.getUid();
                mDatabase.child("users").child(userID).child("username").setValue(name);
                startActivity(new Intent(getApplicationContext(), ReviewActivity.class));
                finish();
            }
        });
    }
}

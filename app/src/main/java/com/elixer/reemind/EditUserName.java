package com.elixer.reemind;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.elixer.reemind.MainActivity.auth;
import static com.elixer.reemind.MainActivity.tinyDBM;
import static com.elixer.reemind.MainActivity.userID;

public class EditUserName extends AppCompatActivity {
    EditText editText;
    Button button;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_user_name);
        button=(Button)findViewById(R.id.btn_Confirm);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        editText = (EditText) findViewById(R.id.displayName);
        editText.setText(tinyDBM.getString("userNameDisplay"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    final String name = String.valueOf(editText.getText());
                    Toast.makeText(EditUserName.this, "Updating", Toast.LENGTH_LONG).show();

                    DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    FirebaseUser user = auth.getCurrentUser();
                    userID = user.getUid();
                    mDatabase.child("users").child(userID).child("username").setValue(name, new DatabaseReference.CompletionListener() {
                        public void onComplete(DatabaseError error, DatabaseReference ref) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (error == null) {
                                tinyDBM.putString("userNameDisplay", name);

                                setResult(RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(EditUserName.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                                Toast.makeText(EditUserName.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(EditUserName.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                    Toast.makeText(EditUserName.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

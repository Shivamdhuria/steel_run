package com.elixer.reemind;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class EditPhoneNumber extends AppCompatActivity {
    EditText editPhone;
    Button button;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone_number);
        button=(Button)findViewById(R.id.btn_Confirm2);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        editPhone = (EditText) findViewById(R.id.displayPhoneNumber);
        editPhone.setText(tinyDBM.getString("phoneNumberDisplay"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String phone = String.valueOf(editPhone.getText());
                Toast.makeText(EditPhoneNumber.this,"Updating", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.VISIBLE);
                DatabaseReference mDatabase ;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                FirebaseUser user = auth.getCurrentUser();
                userID = user.getUid();
                mDatabase.child("users").child(userID).child("phone").setValue(phone, new DatabaseReference.CompletionListener() {
                    public void onComplete(DatabaseError error, DatabaseReference ref) {

                        progressBar.setVisibility(View.INVISIBLE);
                        if(error == null) {
                            tinyDBM.putString("phoneNumberDisplay",phone);
                            setResult(RESULT_OK);
                            finish();
                        }
                        else{
                            Toast.makeText(EditPhoneNumber.this,"Something went wrong!", Toast.LENGTH_LONG).show();
                            Toast.makeText(EditPhoneNumber.this,"Check your internet connection", Toast.LENGTH_LONG).show();
                        }

                    }
                });


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

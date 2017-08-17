package com.example.admin.import2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hbb20.CountryCodePicker;

import static java.security.AccessController.getContext;

public class Register extends AppCompatActivity {

    //for authorization
    private FirebaseAuth auth;
    //for reading and writing to database
    private DatabaseReference mDatabase;

    private Button btn_submit;
    private EditText inputName, inputPhone,inputCountryCode;
    String userID;
    CountryCodePicker ccp;
    String countryCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

      if (auth.getCurrentUser() == null) {
            startActivity(new Intent(Register.this, MainActivity.class));
            finish();
        }


        // Setting the view now
        setContentView(R.layout.activity_register);
        //Get Firebase database instant
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user=auth.getCurrentUser();
        userID = user.getUid();
        inputName=(EditText)findViewById(R.id.inputName);
        inputPhone=(EditText)findViewById(R.id.inputPhone);
        inputCountryCode=(EditText)findViewById(R.id.inputCountryCode);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        String countryCode = GetCountryZipCode();
        Log.d("country code",countryCode);
        inputCountryCode.setText("+"+GetCountryZipCode());





        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String phone = inputPhone.getText().toString();
                 MainActivity.tokenID = FirebaseInstanceId.getInstance().getToken();
                //Handling exceptions
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length()<=9) {
                    Toast.makeText(getApplicationContext(), "Phone Number should be 10 digits long", Toast.LENGTH_SHORT).show();
                    return;
                }


                    String phoneUpdated = ccp.getSelectedCountryName();
                Log.d("phone nmber update",phoneUpdated);
                // Creating new user node,
                    User user = new User(name, phoneUpdated,MainActivity.tokenID);

                    mDatabase.child("users").child(userID).setValue(user);
                    Toast.makeText(getApplicationContext(),"Info Updated",Toast.LENGTH_SHORT).show();

                Log.d("fcm ",MainActivity.tokenID);
                FirebaseMessaging.getInstance().subscribeToTopic(userID);
                    startActivity(new Intent(Register.this, MainActivity.class));
                     finish();



            }
        });
    }
    public String GetCountryZipCode(){
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID=manager.getSimCountryIso().toUpperCase();
        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
    }



}

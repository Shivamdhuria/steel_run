package com.example.admin.import2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.admin.import2.MainActivity.tinyDBM;

public class ReviewActivity extends AppCompatActivity {
    TextView displayName;
    TextView displayPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        displayName = (TextView)findViewById(R.id.displayName);

        displayPhone = (TextView)findViewById(R.id.displayPhone);
        displayName.setText(tinyDBM.getString("userNameDisplay"));
        displayPhone.setText(tinyDBM.getString("phoneNumberDisplay"));
    }
}

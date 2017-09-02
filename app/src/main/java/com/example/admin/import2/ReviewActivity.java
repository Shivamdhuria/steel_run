package com.example.admin.import2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.admin.import2.MainActivity.tinyDB;
import static com.example.admin.import2.MainActivity.tinyDBM;

public class ReviewActivity extends AppCompatActivity {
    TextView displayName;
    TextView displayPhone;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        displayName = (TextView)findViewById(R.id.displayName);
        icon=(ImageView)findViewById(R.id.profile_image);

        displayPhone = (TextView)findViewById(R.id.displayPhone);
        displayName.setText(tinyDBM.getString("userNameDisplay"));
        displayPhone.setText(tinyDBM.getString("phoneNumberDisplay"));
        String userPicture =tinyDBM.getString("displayPicture");

        //Setting Users picture
        if (!userPicture.equals("null")) {


            byte[] decodedString = Base64.decode(userPicture, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            icon.setImageBitmap(decodedByte);

        }

    }
}

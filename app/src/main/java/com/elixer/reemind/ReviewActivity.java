package com.elixer.reemind;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.elixer.reemind.MainActivity.auth;
import static com.elixer.reemind.MainActivity.tinyDBM;
import static com.elixer.reemind.MainActivity.userID;
import static com.elixer.reemind.MainActivity.userPicture;

public class ReviewActivity extends AppCompatActivity {
    TextView displayName;
    TextView displayPhone;
    ImageView icon;
    private ImageButton imageButtonEditName;
    private ImageButton imageButtonEditPhone;
    private ImageButton button_profilepicture;
    private static final int PICK_IMAGE_REQUEST = 1;
    Bitmap ThumbImage;
    String base64Image;
    Button button_signout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        displayName = (TextView) findViewById(R.id.displayName);
        icon = (ImageView) findViewById(R.id.profile_image);
        button_profilepicture = (ImageButton) findViewById(R.id.button_profilepicture);
        imageButtonEditName =(ImageButton)findViewById(R.id.imageButton_Name);
        imageButtonEditPhone=(ImageButton)findViewById(R.id.imageButton_Phone);
        button_signout=(Button)findViewById(R.id.btn_signout);
        progressBar= (ProgressBar)findViewById(R.id.progressBar);

        displayPhone = (TextView) findViewById(R.id.displayPhoneNumber);
        displayName.setText(tinyDBM.getString("userNameDisplay"));
        displayPhone.setText(tinyDBM.getString("phoneNumberDisplay"));
        String userPicture = tinyDBM.getString("displayPicture");

        //Setting Users picture
        if (!userPicture.equals("null")) {


            byte[] decodedString = Base64.decode(userPicture, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            icon.setImageBitmap(decodedByte);

        }

        imageButtonEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditUserName.class);
                startActivityForResult(intent,RESULT_OK);


            }
        });

        imageButtonEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditPhoneNumber.class);
                startActivityForResult(intent,RESULT_OK);


            }
        });

        button_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


        button_profilepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChooseApp();


            }


        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                // String path = getPathFromURI(selectedImageUri);
                ThumbImage = ThumbnailUtils.extractThumbnail(selectedImage, 320, 320);
               icon=(ImageView)findViewById(R.id.profile_image);
                icon.setImageBitmap(ThumbImage);
                if(isNetworkAvailable()) {

                    ConvertAndUpload(ThumbImage);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"Check your internet connection", Toast.LENGTH_LONG).show();
                }










                Log.d("ImageWidth = " + ThumbImage.getWidth(), "ImageHeight = "
                        + ThumbImage.getHeight());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void ConvertAndUpload(Bitmap thumbImage) {
        DatabaseReference mDatabase ;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = auth.getCurrentUser();
        userID = user.getUid();


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // shrink it down otherwise we will use stupid amounts of memory

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

        // we finally have our base64 string version of the image, save it.
        mDatabase.child("users").child(userID).child("userpicture").setValue(base64Image,new DatabaseReference.CompletionListener() {

            public void onComplete(DatabaseError error, DatabaseReference ref) {

                if(error == null) {
                    userPicture = base64Image;
                    tinyDBM.putString("displayPicture", base64Image);
                    Toast.makeText(getApplicationContext(), "Display picture changed successfully", Toast.LENGTH_LONG).show();
                }



                else{
                    Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"Check your internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });


    }






    public void ChooseApp(){

        // Create intent to Open Image applications like Gallery, Google Photos

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,

                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Start the Intent
        Log.d("pick image request", String.valueOf(PICK_IMAGE_REQUEST));
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        displayPhone.setText(tinyDBM.getString("phoneNumberDisplay"));
        displayName.setText(tinyDBM.getString("userNameDisplay"));
        super.onResume();
        //Refresh your stuff here
        displayName.setText(tinyDBM.getString("userNameDisplay"));
        displayPhone.setText(tinyDBM.getString("phoneNumberDisplay"));
    }

    public void signOut() {
        auth.signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        ReviewActivity.this.finish();


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void killActivity() {
        finish();
    }


}



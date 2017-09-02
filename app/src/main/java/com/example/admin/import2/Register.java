package com.example.admin.import2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.StorageReference;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.media.ThumbnailUtils.extractThumbnail;
import static com.example.admin.import2.MainActivity.tinyDBM;
import static com.example.admin.import2.MainActivity.userPicture;

public class Register extends AppCompatActivity {

    //for authorization
    private FirebaseAuth auth;
    //for reading and writing to database
    private DatabaseReference mDatabase;

    private Button btn_submit;
    private EditText inputName, inputPhone, inputCountryCode;
    String userID;

    String countryCode;
    String phoneUpdated;
     private ImageButton button_profilepicture;
    //a constant to track the file chooser intent
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    ImageView imageview_profliepicture;
    String img_Decodable_Str;
    String imgDecodableString;
    private StorageReference mStorageRef;
    Bitmap ThumbImage;
    Uri imageUri;
    protected String base64Image="null";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //initialze tinyDB
        imageview_profliepicture = (ImageView)findViewById(R.id.profile_image);



        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(Register.this, MainActivity.class));
            finish();
        }


        // Setting the view now
        setContentView(R.layout.activity_register);
        //Get Firebase database instant
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = auth.getCurrentUser();
        userID = user.getUid();
        inputName = (EditText) findViewById(R.id.displayName);
        inputPhone = (EditText) findViewById(R.id.displayPhone);
        inputCountryCode = (EditText) findViewById(R.id.inputCountryCode);
        btn_submit = (Button) findViewById(R.id.btn_edit);
        button_profilepicture = (ImageButton) findViewById(R.id.button_profilepicture);
        String countryCode = GetCountryZipCode();
        Log.d("country code", countryCode);
        inputCountryCode.setText("+" + GetCountryZipCode());
        tinyDBM.putString("displayPicture","null");






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
                if (phone.length() <= 9) {
                    Toast.makeText(getApplicationContext(), "Phone Number should be 10 digits long", Toast.LENGTH_SHORT).show();
                    return;
                }


                String countryCodeInput = inputCountryCode.getText().toString();
                phoneUpdated = countryCodeInput + phone;
                //Saving name and phonen umber in Database
                tinyDBM.putString("userNameDisplay", name);
                tinyDBM.putString("phoneNumberDisplay", phoneUpdated);
                Log.d("phone upload", phoneUpdated);
                Log.d("phone nmber update", phoneUpdated);
                // Creating new user node,
                User user = new User(name, phoneUpdated, MainActivity.tokenID,base64Image);



                mDatabase.child("users").child(userID).setValue(user);
                Toast.makeText(getApplicationContext(), "Info Updated", Toast.LENGTH_SHORT).show();

                Log.d("fcm ", MainActivity.tokenID);
                FirebaseMessaging.getInstance().subscribeToTopic(userID);
                startActivity(new Intent(Register.this, MainActivity.class));
                finish();


            }
        });


        button_profilepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseApp();



            }


        });

        //handling the image chooser activity result

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
               // String path = getPathFromURI(selectedImageUri);
                ThumbImage = ThumbnailUtils.extractThumbnail(selectedImage, 320, 320);
                imageview_profliepicture=(ImageView)findViewById(R.id.profile_image);
                imageview_profliepicture.setImageBitmap(ThumbImage);
                ConvertAndUpload(ThumbImage);











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
        mDatabase.child("users").child(userID).setValue(base64Image);
        userPicture = base64Image;
        tinyDBM.putString("displayPicture",base64Image);

        System.out.println("Stored image with length: " + bytes.length);
        Log.d("userID",userID);
    }





    public void ChooseApp(){

        // Create intent to Open Image applications like Gallery, Google Photos

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,

                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Start the Intent
        Log.d("pick image request", String.valueOf(PICK_IMAGE_REQUEST));
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }




    public String GetCountryZipCode() {
        String CountryID = "";
        String CountryZipCode = "";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID = manager.getSimCountryIso().toUpperCase();
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(CountryID.trim())) {
                CountryZipCode = g[0];
                break;
            }
        }
        return CountryZipCode;
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Register Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

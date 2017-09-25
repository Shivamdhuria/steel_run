package com.elixer.reemind;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 8/23/2017.
 */

public class CustomAdapter  extends BaseAdapter {

    ArrayList<String> listItem;
    ArrayList<String> listIcon;

    Context mContext;

    //constructor
    public CustomAdapter(Context mContext, ArrayList<String> listItem,ArrayList listIcon) {
        this.mContext = mContext;
        this.listItem = listItem;
        this.listIcon= listIcon;

    }

    public int getCount() {
        return listItem.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View arg1, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_single, viewGroup, false);

        TextView name = (TextView) row.findViewById(R.id.name);
        ImageView icon = (ImageView)row.findViewById(R.id.image);
       name.setText(listItem.get(position));
       // Log.d("encoded",listIcon.get(0).toString());
       String encodedImage = listIcon.get(position);

        try{
        if (!encodedImage.equals("null")) {


            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            icon.setImageBitmap(decodedByte);

        }}
        catch (Exception er){

        }


        return row;
    }
}
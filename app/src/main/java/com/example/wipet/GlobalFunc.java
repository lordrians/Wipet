package com.example.wipet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class GlobalFunc {

    public static void showToast(String message, Context mContext, String length){
        if (length.equals(GlobalVar.TIME_LONG_TOAST)){
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static String bitmapToString(Bitmap bitmap){
        if (bitmap != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] imageByte = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imageByte, Base64.DEFAULT);
        } else {
            return "";
        }
    }

    public static HashMap<String,String> getHeaders(Context mContext){
        SharedPreferences userInfo = mContext.getSharedPreferences(GlobalVar.SP_USER_INFO,0);
        String token = userInfo.getString(GlobalVar.VAR_TOKEN,"");

        HashMap<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        return map;
    }

    public static Bitmap stringToBitmap(String photo){
        try {
            byte[] encodeByte = Base64.decode(photo, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
            return bitmap;
        } catch (Exception e){
            e.getMessage();
            return null;
        }

    }

    @SuppressLint("SimpleDateFormat")
    public static String timeToString(String created_at) {
        String dateString = null;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSS'Z'").parse(created_at);
            long mili = date.getTime();
            dateString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static int Utility(Context mContext) {

        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / 180 + 0.5); // +0.5 for correct rounding to int.
        return noOfColumns;

    }

    @SuppressLint("SimpleDateFormat")
    public static String convertTime(String created_at)  {
        String ago = null;
        try {

            String dateString = null;
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSS'Z'").parse(created_at);
            long mili = date.getTime();
            Long now = System.currentTimeMillis();
            ago = DateUtils.getRelativeTimeSpanString(mili, now, DateUtils.MINUTE_IN_MILLIS).toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return ago;
    }

    public static String getFormatCurrency(long price){
        NumberFormat numberFormat = new DecimalFormat("#,###");
        return numberFormat.format(price);
    }

}

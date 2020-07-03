package com.example.wipet;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
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
    

}

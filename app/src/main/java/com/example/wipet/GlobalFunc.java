package com.example.wipet;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class GlobalFunc {

    public static void showToast(String message, Context mContext, String length){
        if (length.equals(GlobalVar.TIME_LONG_TOAST)){
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    

}

package com.example.wipet.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;
import com.example.wipet.activity.ListAdoptionActivity;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private ArrayList<String> arrayListPhoto;
    private Context mContext;
    private int format;
    private String activity;
    private View parent;

    public PhotoAdapter(Context mContext, ArrayList<String> arrayListPhoto, int format,String activity){
        this.mContext = mContext;
        this.arrayListPhoto = arrayListPhoto;
        this.format = format;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_vp_photo, parent, false);
        parent = (ViewGroup) parent.getRootView();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (format == GlobalVar.STRING_FORMAT){
            if (activity.equals(GlobalVar.DETAIL_DISCUSSION)){
                Glide.with(mContext)
                        .load(Api.DIR_DISCUSSION_PHOTO + arrayListPhoto.get(position))
                        .into(holder.ivPhoto);
            } else if (activity.equals(GlobalVar.DETAIL_ADOPTION)) {
                Glide.with(mContext)
                        .load(Api.DIR_ADOPTION_PHOTO + arrayListPhoto.get(position))
                        .into(holder.ivPhoto);
            }

        } else {
            ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
            for (int i = 0; i < arrayListPhoto.size(); i++){
                bitmapArrayList.add(GlobalFunc.stringToBitmap(arrayListPhoto.get(i)));
            }
            holder.ivPhoto.setImageBitmap(GlobalFunc.stringToBitmap(arrayListPhoto.get(position)));
        }

    }

    @Override
    public int getItemCount() {
        return arrayListPhoto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo_item_vp);
        }
    }
}

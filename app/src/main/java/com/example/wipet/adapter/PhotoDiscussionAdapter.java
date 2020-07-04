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
import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;

import java.util.ArrayList;

public class PhotoDiscussionAdapter extends RecyclerView.Adapter<PhotoDiscussionAdapter.ViewHolder> {
    private ArrayList<String> arrayListPhoto;
    private Context mContext;
    private int format;
    private View parent;

    public PhotoDiscussionAdapter(Context mContext, ArrayList<String> arrayListPhoto, int format){
        this.mContext = mContext;
        this.arrayListPhoto = arrayListPhoto;
        this.format = format;
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
            Glide.with(mContext)
                    .load(arrayListPhoto.get(position).toString())
                    .into(holder.ivPhoto);
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

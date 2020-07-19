package com.example.wipet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;
import com.example.wipet.activity.DetailDiscussionActivity;
import com.example.wipet.object.Discussion;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DiscussionHorizontalAdapter extends RecyclerView.Adapter<DiscussionHorizontalAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Discussion> discussionArrayList;
    private View view;

    public DiscussionHorizontalAdapter(Context mContext, ArrayList<Discussion> discussionArrayList) {
        this.mContext = mContext;
        this.discussionArrayList = discussionArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_discussion_horizontal,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Discussion discussion = discussionArrayList.get(position);

        holder.tvTitle.setText(discussion.getTitle());
        holder.tvUsername.setText(discussion.getUser().getUsername());

        holder.tvDate.setText(GlobalFunc.timeToString(discussion.getCreated_at()));
        holder.tvLike.setText(String.valueOf(discussion.getLikesCount()));
        holder.tvComment.setText(String.valueOf(discussion.getCommentsCount()));
        if (discussion.getPhoto() != null){

            Glide.with(mContext)
                    .load(Api.DIR_DISCUSSION_PHOTO + discussion.getPhoto().get(0))
                    .into(holder.ivPhoto);
        } else {
            holder.ivPhoto.setImageResource(R.drawable.ic_cats);
        }
        holder.itemLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailDiscussionActivity.class);
            intent.putExtra("discussion", discussion);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return discussionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private TextView tvTitle, tvUsername, tvDate, tvLike, tvComment;
        private CardView itemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo_item_dishor);
            tvTitle = itemView.findViewById(R.id.tv_item_title_dishor);
            tvUsername = itemView.findViewById(R.id.tv_username_item_dishor);
            tvDate = itemView.findViewById(R.id.tv_date_item_dishor);
            tvLike = itemView.findViewById(R.id.tv_like_item_dishor);
            tvComment = itemView.findViewById(R.id.tv_comment_item_dishor);
            itemLayout = itemView.findViewById(R.id.item_layout_dischorizontal);

        }
    }
}

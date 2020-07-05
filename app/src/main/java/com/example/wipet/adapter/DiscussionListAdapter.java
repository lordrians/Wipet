package com.example.wipet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wipet.Api;
import com.example.wipet.R;
import com.example.wipet.activity.DetailDiscussionActivity;
import com.example.wipet.object.Discussion;
import com.example.wipet.object.User;

import java.util.ArrayList;

public class DiscussionListAdapter extends RecyclerView.Adapter<DiscussionListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Discussion> discussionArrayList;
    private View view;

    public DiscussionListAdapter(Context mContext, ArrayList<Discussion> discussionArrayList){
        this.discussionArrayList = discussionArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_discussion_list_vertical,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Discussion discussion = discussionArrayList.get(position);

        holder.tvTitle.setText(discussion.getTitle());
        holder.tvUsername.setText(discussion.getUser().getUsername());
        holder.tvDate.setText(discussion.getCreated_at());
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
        private TextView tvTitle, tvUsername, tvDate, tvLike, tvComment;
        private ImageView ivPhoto;
        private LinearLayout itemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_item_discvertical);
            tvUsername = itemView.findViewById(R.id.tv_username_item_discvertical);
            tvDate = itemView.findViewById(R.id.tv_date_item_discvertical);
            tvLike = itemView.findViewById(R.id.tv_like_item_discvertical);
            tvComment = itemView.findViewById(R.id.tv_comment_item_discvertical);
            ivPhoto = itemView.findViewById(R.id.iv_photo_item_discvertical);

            itemLayout = itemView.findViewById(R.id.item_layout_discvertical);


        }
    }
}

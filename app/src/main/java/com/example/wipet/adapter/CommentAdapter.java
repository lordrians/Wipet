package com.example.wipet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wipet.Api;
import com.example.wipet.R;
import com.example.wipet.object.Comment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context mContext;
    private View view;
    private ArrayList<Comment> commentArrayList;

    public CommentAdapter(Context mContext, ArrayList<Comment> commentArrayList){
        this.mContext = mContext;
        this.commentArrayList = commentArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Comment comment = commentArrayList.get(position);

        holder.tvUsername.setText(comment.getUser().getUsername());
        holder.tvComment.setText(comment.getComment());
        holder.tvDate.setText(comment.getCreated_at());

        if (comment.getUser().getPhoto() != null){
            Glide.with(mContext)
                    .load(Api.DIR_USER_PHOTO + comment.getUser().getPhoto())
                    .centerCrop()
                    .into(holder.ivPhoto);
        } else {
            holder.ivPhoto.setImageResource(R.color.grey);
        }

    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivPhoto;
        private TextView tvUsername, tvDate, tvComment;
        private LinearLayout itemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo_item_comment);
            tvUsername = itemView.findViewById(R.id.tv_username_item_comment);
            tvDate = itemView.findViewById(R.id.tv_date_item_comment);
            tvComment = itemView.findViewById(R.id.tv_content_item_comment);
            itemLayout = itemView.findViewById(R.id.item_layout_comment);



        }
    }
}

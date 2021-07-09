package com.example.instagramapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    List<Comment> rvComments;
    public static final String KEY_PROFILE = "profilePic";

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.rvComments = commentList;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment = rvComments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return rvComments.size();
    }

    public void clear() {
        rvComments.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Comment> list) {
        rvComments.addAll(list);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPicture;
        TextView tvUser;
        TextView tvComment;
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.ivPPicture);
            tvUser = itemView.findViewById(R.id.tvUserC);
            tvComment = itemView.findViewById(R.id.tvBodyCom);
            tvTime = itemView.findViewById(R.id.tvRelative);
        }

        public void bind(Comment comment) {
            tvUser.setText(comment.getUser().getUsername());
            Glide.with(context).load(comment.getUser().getParseFile(KEY_PROFILE).getUrl()).circleCrop().into(ivPicture);
            tvComment.setText(comment.getComment());
            Date createdAt = comment.getCreatedAt();
            String timeAgo = Comment.calculateTimeAgo(createdAt);
            tvTime.setText(timeAgo);
        }
    }
}

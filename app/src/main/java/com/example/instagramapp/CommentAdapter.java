package com.example.instagramapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    List<Comment> rvComments;

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
        }
    }
}
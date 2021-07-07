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

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    List<Post> rvPosts;

    public PostAdapter(Context context, List<Post> rvPosts) {
        this.context = context;
        this.rvPosts = rvPosts;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post post = rvPosts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return rvPosts.size();
    }

    public void clear() {
        rvPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        rvPosts.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername;
        TextView tvUserBody;
        TextView tvBody;
        ImageView ivPict;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvBody = itemView.findViewById(R.id.tvBody);
            ivPict = itemView.findViewById(R.id.ivPict);
            tvUserBody = itemView.findViewById(R.id.tvUserBody);
        }

        public void bind(Post post) {
            tvUsername.setText(post.getUser().getUsername());
            if (post.getImage() != null){
                Glide.with(context).load(post.getImage().getUrl()).into(ivPict);
            }
            tvUserBody.setText(post.getUser().getUsername());
            tvBody.setText(post.getDescription());
        }
    }
}

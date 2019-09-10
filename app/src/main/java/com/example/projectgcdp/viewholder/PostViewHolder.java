package com.example.projectgcdp.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectgcdp.Model.Post;
import com.example.projectgcdp.R;


public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public ImageView imagepost;
    public TextView numStarsView;
    public TextView bodyView;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.postTitle);
        authorView = itemView.findViewById(R.id.postAuthor);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.postNumStars);
        bodyView = itemView.findViewById(R.id.postBody);
        imagepost = itemView.findViewById(R.id.impostitem);
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.starCount));
        bodyView.setText(post.body);
        starView.setOnClickListener(starClickListener);
        



    }
}

package com.example.projectgcdp.fragment;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

public class MyTopPostsFragment extends PostListFragment {

    public MyTopPostsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START my_top_posts_query]
        // My top posts by number of stars
        String myUserId = getUid();
        Query myTopPostsQuery = databaseReference.child("user-posts").child(myUserId)
                .orderByChild("starCount");
        // [END my_top_posts_query]

        return myTopPostsQuery;
    }

//    @Override
//    public Task<Uri> getPicture(StorageReference storageReference) {
//        Task<Uri> recentPicturePostsQuery = storageReference.child("picture-Post").getDownloadUrl();
//        return recentPicturePostsQuery;
//
//    }


}

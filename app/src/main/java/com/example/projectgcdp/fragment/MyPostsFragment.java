package com.example.projectgcdp.fragment;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

public class MyPostsFragment extends PostListFragment {

    public MyPostsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-posts")
                .child(getUid());
    }

//    @Override
//    public Task<Uri> getPicture(StorageReference storageReference) {
//        Task<Uri> recentPicturePostsQuery = storageReference.child("picture-Post").getDownloadUrl();
//        return recentPicturePostsQuery;
//    }


}

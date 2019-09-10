package com.example.projectgcdp.fragment;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

public class RecentPostsFragment extends PostListFragment {

    public RecentPostsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(100);
        // [END recent_posts_query]

        return recentPostsQuery;
    }


}

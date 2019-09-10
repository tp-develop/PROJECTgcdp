package com.example.projectgcdp.FragUsage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyUsageFragment extends UsageListFragment {

    public MyUsageFragment() {}

    @Override
    public Query getQueryUsage(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-usage")
                .child(getUid());
    }
}

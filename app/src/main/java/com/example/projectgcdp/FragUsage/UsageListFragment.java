package com.example.projectgcdp.FragUsage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectgcdp.Model.Post;
import com.example.projectgcdp.Model.UsageModel;
import com.example.projectgcdp.PostDetailActivity;
import com.example.projectgcdp.R;
import com.example.projectgcdp.UseageHistory.Usage;
import com.example.projectgcdp.viewholder.PostViewHolder;
import com.example.projectgcdp.viewholder.UsageViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public abstract class UsageListFragment extends Fragment {
    private static final String TAG = "PostListFragment";



    // [START define_database_reference]
    private DatabaseReference mDatabase;


    // [END define_database_reference]

    private FirebaseRecyclerAdapter<UsageModel, UsageViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;


    public UsageListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_usage, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        storageReference = FirebaseStorage.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = rootView.findViewById(R.id.messagesListUsage);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query usagesQuery = getQueryUsage(mDatabase);
//        Task<Uri> getPicture = getPicture(storageReference);



        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Usage>()
                .setQuery(usagesQuery, Usage.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<UsageModel, UsageViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull UsageViewHolder usageViewHolder, int position, @NonNull final UsageModel usageModel) {
                final DatabaseReference usageRef = getRef(position);

                usageViewHolder.numuse.setText(position);

                final String usageKey = usageRef.getKey();
                usageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                        intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, usageKey);
                        startActivity(intent);
                    }
                });

                usageViewHolder.bindToUsage(usageModel, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored

                        DatabaseReference userPostRef = mDatabase.child("user-usage").child(usageModel.uid).child(usageRef.getKey());

                        // Run two transactions
                        onStarClicked(userPostRef);

                    }
                });
            }

            @Override
            public UsageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new UsageViewHolder(inflater.inflate(R.layout.item_usage, viewGroup, false));
            }


        };
        mRecycler.setAdapter(mAdapter);
    }

    // [START post_stars_transaction]
    private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                UsageModel p = mutableData.getValue(UsageModel.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

//                if (p.stars.containsKey(getUid())) {
//                    // Unstar the post and remove self from stars
//                    p.starCount = p.starCount - 1;
//                    p.stars.remove(getUid());
//                } else {
//                    // Star the post and add self to stars
//                    p.starCount = p.starCount + 1;
//                    p.stars.put(getUid(), true);
//                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
    // [END post_stars_transaction]


    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQueryUsage(DatabaseReference databaseReference);

}

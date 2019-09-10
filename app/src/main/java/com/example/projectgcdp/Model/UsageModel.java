package com.example.projectgcdp.Model;

import android.widget.ImageView;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UsageModel {

    public String uid;
    public String numuse;
    public String date;
    public String result;
    public Map<String, Boolean> stars = new HashMap<>();

    public UsageModel() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public UsageModel(String uid, String numuse, String date, String result) {
        this.uid = uid;
        this.numuse = numuse;
        this.date = date;
        this.result = result;

    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("numuse", numuse);
        result.put("date", date);
        result.put("result", result);



        return result;
    }
}

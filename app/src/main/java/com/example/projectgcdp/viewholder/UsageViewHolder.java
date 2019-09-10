package com.example.projectgcdp.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectgcdp.Model.Post;
import com.example.projectgcdp.Model.UsageModel;
import com.example.projectgcdp.R;
import com.example.projectgcdp.UseageHistory.Usage;

public class UsageViewHolder extends RecyclerView.ViewHolder {


    public TextView numuse;
    public TextView date;
    public ImageView ImageResult;


    public UsageViewHolder(View itemView) {
        super(itemView);

        numuse = itemView.findViewById(R.id.numUsage);
        date = itemView.findViewById(R.id.dateUsage);
        ImageResult = itemView.findViewById(R.id.imresult);

    }

    public void bindToUsage(UsageModel usageModel, View.OnClickListener starClickListener) {
        numuse.setText(usageModel.numuse);
        date.setText(usageModel.date);





    }
}

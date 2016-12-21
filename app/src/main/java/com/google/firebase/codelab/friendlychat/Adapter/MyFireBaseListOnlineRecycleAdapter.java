package com.google.firebase.codelab.friendlychat.Adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.StatusMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Khanh Tran-Cong on 12/19/2016.
 * Email: congkhanh197@gmail.com
 */

public class MyFireBaseListOnlineRecycleAdapter extends RecyclerView.Adapter<MyFireBaseListOnlineRecycleAdapter.StatusHolder> {

    MyFirebaseArray mSnapshot;
    String mUserId;

    public MyFireBaseListOnlineRecycleAdapter(DatabaseReference ref, final String userId) {
        mSnapshot = new MyFirebaseArray(ref){
            @Override
            protected boolean filter(DataSnapshot dataSnapshot, String previousChildKey) {
                return !dataSnapshot.getKey().equals(userId);
            }
        };
        mSnapshot.setOnChangedListener(new MyFirebaseArray.OnChangedListener() {
            @Override
            public void onChanged(EventType type, int index, int oldIndex) {
                switch (type) {
                    case Added:
                        notifyItemInserted(index);
                        break;
                    case Removed:
                        notifyItemRemoved(index);
                        break;
                    case Changed:
                        notifyItemChanged(index);
                        break;
                    case Moved:
                        notifyItemMoved(oldIndex, index);
                        break;
                    default:
                        throw new IllegalStateException("Incomplete case statement");
                }
            }
        });
       mUserId = userId;
    }

    @Override
    public StatusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acc_online, parent, false);
        return new StatusHolder(v);
    }

    @Override
    public void onBindViewHolder(StatusHolder holder, int position) {
        StatusMessage statusMessage = getItem(position);
        if (statusMessage != null) {
            holder.accTextView.setText(statusMessage.getName());
            if (statusMessage.getStatus().equals("false"))
                holder.accStatusImageView.setEnabled(false);
            if (statusMessage.getPhotoUrl() == null) {
                holder.accImageView.setImageDrawable(ContextCompat
                        .getDrawable(holder.accImageView.getContext()
                                , R.drawable.ic_account_circle_black_36dp));
            } else
                Glide.with(holder.accImageView.getContext())
                        .load(statusMessage.getPhotoUrl())
                        .into(holder.accImageView);
        }
    }

    public StatusMessage getItem(int position) {
        return mSnapshot.getItem(position).getValue(StatusMessage.class);
    }

    public void cleanup() {
        mSnapshot.cleanup();
    }

    @Override
    public int getItemCount() {
        return mSnapshot.getCount();
    }

    public class StatusHolder extends RecyclerView.ViewHolder {
        public TextView accTextView;
        public CircleImageView accImageView;
        public CircleImageView accStatusImageView;

        public StatusHolder(View v) {
            super(v);
            accTextView = (TextView) v.findViewById(R.id.accName);
            accImageView = (CircleImageView) v.findViewById(R.id.accImageView);
            accStatusImageView = (CircleImageView) v.findViewById(R.id.accStatusImageView);
            accStatusImageView.setEnabled(true);
        }
    }
}

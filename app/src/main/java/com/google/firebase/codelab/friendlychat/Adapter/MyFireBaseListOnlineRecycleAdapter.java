package com.google.firebase.codelab.friendlychat.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.codelab.friendlychat.StatusMessage;
import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Khanh Tran-Cong on 12/19/2016.
 * Email: congkhanh197@gmail.com
 */

public class MyFireBaseListOnlineRecycleAdapter extends RecyclerView.Adapter<MyFireBaseListOnlineRecycleAdapter.ViewHolder> {

    MyFirebaseArray mSnapshot;
    String mUserName;

    public MyFireBaseListOnlineRecycleAdapter(DatabaseReference ref, String userName) {
        mSnapshot = new MyFirebaseArray(ref) {

        };
        mSnapshot.setOnChangedListener(new MyFirebaseArray.OnChangedListener() {
            @Override
            public void onChanged(EventType type, int index, int oldIndex) {
                StatusMessage statusMessage = mSnapshot.getItem(index).getValue(StatusMessage.class);
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
        mUserName = userName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acc_online, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView accTextView;
        public CircleImageView accImageView;
        public CircleImageView accStatusImageView;

        public ViewHolder(View v) {
            super(v);
            accTextView = (TextView) v.findViewById(R.id.accName);
            accImageView = (CircleImageView) v.findViewById(R.id.accImageView);
            accStatusImageView = (CircleImageView) v.findViewById(R.id.accStatusImageView);
        }
    }
}

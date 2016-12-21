package com.google.firebase.codelab.friendlychat.Adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.codelab.friendlychat.FriendlyMessage;
import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Khanh Tran-Cong on 12/19/2016.
 * Email: congkhanh197@gmail.com
 */

public class MyFirebaseMessageRecycleAdapter extends RecyclerView.Adapter<MyFirebaseMessageRecycleAdapter.MessageViewHolder> {

    protected static final int MY_MESSAGE = 1,OTHER_MESSAGE = 0;
    private String mUserName;
    private MyFirebaseArray mSnapshots;

    protected MyFirebaseMessageRecycleAdapter(DatabaseReference ref, String userName) {
        mSnapshots = new MyFirebaseArray(ref);

        mSnapshots.setOnChangedListener(new MyFirebaseArray.OnChangedListener() {
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
        mUserName = userName;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType == MY_MESSAGE ? R.layout.item_message_user : R.layout.item_message, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        FriendlyMessage friendlyMessage = getItem(position);
        if (friendlyMessage != null) {
            holder.itemView.setTag(position);
            holder.messageEditText.setText(friendlyMessage.getText());
            holder.messengerTextView.setText(friendlyMessage.getName());
            if (friendlyMessage.getPhotoUrl() == null) {
                holder.messengerImageView
                        .setImageDrawable(ContextCompat
                                .getDrawable(holder.messengerImageView.getContext(),
                                        R.drawable.ic_account_circle_black_36dp));
            } else {
                Glide.with(holder.messengerImageView.getContext())
                        .load(friendlyMessage.getPhotoUrl())
                        .into(holder.messengerImageView);
            }
        }
    }

    public void cleanup() {
        mSnapshots.cleanup();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getName().equals(mUserName) ? MY_MESSAGE : OTHER_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return mSnapshots.getCount();
    }

    public FriendlyMessage getItem(int position) {
        FriendlyMessage friendlyMessage =
                mSnapshots.getItem(position).getValue(FriendlyMessage.class);
        friendlyMessage.setId(mSnapshots.getItem(position).getKey());
        return friendlyMessage;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public EditText messageEditText;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;

        public MessageViewHolder(View v) {
            super(v);
            messageEditText = (EditText) v.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) v.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) v.findViewById(R.id.messengerImageView);
        }
    }
}


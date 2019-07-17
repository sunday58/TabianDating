package com.example.andriod.tabiandating;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.andriod.tabiandating.models.Message;
import com.example.andriod.tabiandating.models.User;
import com.example.andriod.tabiandating.util.Messages;

import java.util.ArrayList;

public class MessagesRecyclerViewAdapter extends RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "ConnectionsAdapter";

    //vars
    private ArrayList<User> mUsers = new ArrayList<>();
    private ArrayList<User> mFilteredUsers = new ArrayList<>();
    private Context mContext;
    private IMainActivity mInterface;


    public MessagesRecyclerViewAdapter(Context context, ArrayList<User> users) {
        mContext = context;
        mUsers = users;
        mFilteredUsers = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_messages_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        final User user = mFilteredUsers.get(position);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(mContext)
                .load(user.getProfile_image())
                .apply(requestOptions)
                .into(holder.image);

        holder.name.setText(user.getName());
        holder.message.setText(Messages.MESSAGES[position]); //generate a random message

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + user.getName());
                mInterface.onMessageSelected(new Message(mUsers.get(position), Messages.MESSAGES[position]));

            }
        });
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredUsers = mUsers;
                } else {
                    ArrayList<User> filteredList = new ArrayList<>();
                    for (User row : mUsers) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mFilteredUsers = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredUsers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredUsers = (ArrayList<User>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInterface = (IMainActivity) mContext;
    }

    @Override
    public int getItemCount() {
        return mFilteredUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView message;
        TextView reply;
        RelativeLayout parent;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            reply = itemView.findViewById(R.id.reply);
            parent = itemView.findViewById(R.id.parent_view);
        }
    }
}
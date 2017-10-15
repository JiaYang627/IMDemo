package com.jiayang.imdemo.v.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.bean.User;

import java.util.List;

/**
 * Created by 张 奎 on 2017-10-15 09:08.
 */

public class AddFriendsAdapter extends RecyclerView.Adapter<AddFriendsAdapter.AddFriendsViewHolder> {

    private List<User> mUserList;
    private List<String> mContactsList;
    public AddFriendsAdapter(List<User> userList, List<String> contacts) {
        mUserList = userList;
        mContactsList = contacts;
    }

    @Override
    public AddFriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search, parent, false);
        AddFriendsViewHolder addFriendsViewHolder = new AddFriendsViewHolder(view);
        return addFriendsViewHolder;
    }

    @Override
    public void onBindViewHolder(AddFriendsViewHolder holder, int position) {
        User user = mUserList.get(position);
        final String username = user.getUsername();
        holder.mTvUsername.setText(username);
        holder.mTvTime.setText(user.getCreatedAt());
        //判断当前username是不是已经在好友列表中了
        if (mContactsList.contains(username)){
            holder.mBtnAdd.setText("已经是好友");
            holder.mBtnAdd.setEnabled(false);
        }else{
            holder.mBtnAdd.setText("添加");
            holder.mBtnAdd.setEnabled(true);
        }

        holder.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnAddFriendClickListener != null) {
                    mOnAddFriendClickListener.onAddClick(username);
                }
            }
        });

    }

    public interface OnAddFriendClickListener{
        void onAddClick(String username);
    }
    private OnAddFriendClickListener mOnAddFriendClickListener;
    public void setOnAddFriendClickListener(OnAddFriendClickListener addFriendClickListener){
        this.mOnAddFriendClickListener = addFriendClickListener;
    }

    @Override
    public int getItemCount() {
        return mUserList == null ? 0 :mUserList.size();
    }

    class AddFriendsViewHolder extends RecyclerView.ViewHolder{

        TextView mTvUsername;
        TextView mTvTime;
        Button mBtnAdd;

        public AddFriendsViewHolder(View itemView) {
            super(itemView);
            mTvUsername = (TextView) itemView.findViewById(R.id.tv_username);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mBtnAdd = (Button) itemView.findViewById(R.id.btn_add);
        }
    }
}

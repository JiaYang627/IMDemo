package com.jiayang.imdemo.v.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import com.jiayang.imdemo.R;

import java.util.Date;
import java.util.List;

/**
 * Created by 张 奎 on 2017-10-17 08:55.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {


    private List<EMConversation> mEMConversationList;

    public ConversationAdapter(List<EMConversation> EMConversationList) {
        mEMConversationList = EMConversationList;
    }

    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_conversation, parent, false);
        ConversationViewHolder conversationViewHolder = new ConversationViewHolder(view);
        return conversationViewHolder;
    }

    @Override
    public void onBindViewHolder(ConversationViewHolder holder, int position) {
        final EMConversation emConversation = mEMConversationList.get(position);

        // 聊天对象名字
        String userName = emConversation.getUserName();
        // 未读消息数量
        int unreadMsgCount = emConversation.getUnreadMsgCount();

        EMMessage lastMessage = emConversation.getLastMessage();
        long lastMessageTime = lastMessage.getMsgTime();


        EMTextMessageBody body = (EMTextMessageBody) lastMessage.getBody();
        String lastMessages = body.getMessage();

        holder.mTvUsername.setText(userName);
        holder.mTvMsg.setText(lastMessages);
        holder.mTvTime.setText(DateUtils.getTimestampString(new Date(lastMessageTime)));

        if (unreadMsgCount > 99) {
            holder.mTvUnread.setVisibility(View.VISIBLE);
            holder.mTvUnread.setText("99+");

        } else if (unreadMsgCount > 0) {
            holder.mTvUnread.setVisibility(View.VISIBLE);
            holder.mTvUnread.setText(unreadMsgCount + "");

        } else {
            holder.mTvUnread.setVisibility(View.GONE);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(emConversation);
                }
            }
        });


    }


    private onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;

    }

    public interface onItemClickListener {
        void onItemClick(EMConversation emConversation);
    }




    @Override
    public int getItemCount() {
        return mEMConversationList == null ? 0 :mEMConversationList.size();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder {

        TextView mTvUsername;
        TextView mTvTime;
        TextView mTvMsg;
        TextView mTvUnread;

        public ConversationViewHolder(View itemView) {
            super(itemView);
            mTvUsername = (TextView) itemView.findViewById(R.id.tv_username);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
            mTvUnread = (TextView) itemView.findViewById(R.id.tv_unread);
        }
    }
}

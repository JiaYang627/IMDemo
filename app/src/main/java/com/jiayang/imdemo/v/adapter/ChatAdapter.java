package com.jiayang.imdemo.v.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import com.jiayang.imdemo.R;

import java.util.Date;
import java.util.List;

/**
 * Created by 张 奎 on 2017-10-16 11:53.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<EMMessage> mEMMessageList;

    public ChatAdapter(List<EMMessage> EMMessageList) {
        mEMMessageList = EMMessageList;
    }
    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_receiver, parent, false);
        } else if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_send, parent, false);
        }
        ChatViewHolder chatViewHolder = new ChatViewHolder(view);
        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

        EMMessage emMessage = mEMMessageList.get(position);
        // 消息实体对象 (其实是Json串，环信 内部提供的有转换)
        EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
        String message = body.getMessage();
        holder.mTvMsg.setText(message);

        long msgTime = emMessage.getMsgTime();      // 消息的时间
        holder.mTvTime.setText(DateUtils.getTimestampString(new Date(msgTime)));
        if (position == 0) {
            holder.mTvTime.setVisibility(View.VISIBLE);
        } else {
            EMMessage preMessage = mEMMessageList.get(position - 1);
            long preMsgTime = preMessage.getMsgTime(); // 上条消息的时间
            if (DateUtils.isCloseEnough(preMsgTime, msgTime)) {
                holder.mTvTime.setVisibility(View.GONE);
            } else {
                holder.mTvTime.setVisibility(View.VISIBLE);
            }

        }

        if (emMessage.direct()== EMMessage.Direct.SEND){
            switch (emMessage.status()) {
                case INPROGRESS:
                    holder.mIvState.setVisibility(View.VISIBLE);
                    holder.mIvState.setImageResource(R.drawable.msg_state_animation);
                    AnimationDrawable drawable = (AnimationDrawable) holder.mIvState.getDrawable();
                    if (drawable.isRunning()){
                        drawable.stop();
                    }
                    drawable.start();
                    break;
                case SUCCESS:
                    holder.mIvState.setVisibility(View.GONE);
                    break;
                case FAIL:
                    holder.mIvState.setVisibility(View.VISIBLE);
                    holder.mIvState.setImageResource(R.mipmap.msg_error);
                    break;
            }
        }



    }

    @Override
    public int getItemCount() {
        return mEMMessageList == null ? 0 : mEMMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage emMessage = mEMMessageList.get(position);
        return emMessage.direct() == EMMessage.Direct.RECEIVE ? 0 : 1;
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTime;
        TextView mTvMsg;
        ImageView mIvState;

        public ChatViewHolder(View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
            mIvState = (ImageView) itemView.findViewById(R.id.iv_state);
        }
    }
}


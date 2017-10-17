package com.jiayang.imdemo.v.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayang.imdemo.R;
import com.jiayang.imdemo.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by 张 奎 on 2017-10-09 10:55.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements IContactAdapter {
    private List<String> mData = new ArrayList<>();

    public ContactAdapter() {
    }

    public void setData(List<String> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_adapter, parent, false);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {
        final String contact = mData.get(position);
        holder.mTvUsername.setText(contact);
        String initial = StringUtils.getInitial(contact);
        holder.mTvSection.setText(initial);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(contact, position);
                }

                return true;

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemClick(contact, position);
                }
            }
        });

        if (position == 0) {
            holder.mTvSection.setVisibility(View.VISIBLE);
        } else {
            // 得到上一个条目的首字母
            String preContact = mData.get(position - 1);
            String preInitial = StringUtils.getInitial(preContact);
            if (preInitial.equals(initial)) {
                holder.mTvSection.setVisibility(View.GONE);
            } else {
                holder.mTvSection.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public List<String> getData() {
        return mData;

    }

    private onItemClickListener mOnItemLongClickListener;

    public interface onItemClickListener {
        void onItemLongClick(String contact, int position);

        void onItemClick(String contact, int position);
    }

    public void setOnItemLongClickListener(onItemClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }


    class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView mTvSection;
        TextView mTvUsername;

        public ContactViewHolder(View itemView) {
            super(itemView);
            mTvSection = (TextView) itemView.findViewById(R.id.tv_section);
            mTvUsername = (TextView) itemView.findViewById(R.id.tv_username);
        }
    }
}

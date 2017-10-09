package com.jiayang.imdemo.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.fragment.ContactFragmentPst;
import com.jiayang.imdemo.utils.ToastUtils;
import com.jiayang.imdemo.v.adapter.ContactAdapter;
import com.jiayang.imdemo.v.base.BaseFragment;
import com.jiayang.imdemo.v.iview.IcontactFragmentView;
import com.jiayang.imdemo.widget.ContactLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 张 奎 on 2017-10-02 14:50.
 */

public class ContactFragment extends BaseFragment<ContactFragmentPst> implements IcontactFragmentView {

    @BindView(R.id.contactLayout)
    ContactLayout mContactLayout;
    private Unbinder mUnbinder;
    private ContactAdapter mContactAdapter;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_contact, null);
        mUnbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void fillData(List<String> contactsList) {
        if (mContactAdapter == null) {
            mContactAdapter = new ContactAdapter(contactsList);
        }
        mContactLayout.setAdapter(mContactAdapter);
    }

    @Override
    public void upDateContacts(boolean isSuccess, String msg) {

        if (isSuccess) {
            if (mContactAdapter != null) {
                mContactAdapter.notifyDataSetChanged();
            }
        } else {
            ToastUtils.initToast(msg);
        }
    }
}

package com.jiayang.imdemo.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.fragment.ContactFragmentPst;
import com.jiayang.imdemo.utils.ToastUtils;
import com.jiayang.imdemo.v.adapter.ContactAdapter;
import com.jiayang.imdemo.v.base.BaseFragment;
import com.jiayang.imdemo.v.event.OnContactUpdateEvent;
import com.jiayang.imdemo.v.iview.IcontactFragmentView;
import com.jiayang.imdemo.widget.ContactLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 张 奎 on 2017-10-02 14:50.
 */

public class ContactFragment extends BaseFragment<ContactFragmentPst> implements IcontactFragmentView, SwipeRefreshLayout.OnRefreshListener, ContactAdapter.onItemClickListener {

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

        mContactLayout.setSwipeRefreshOnRefresh(this);

        EventBus.getDefault().register(this);
        if (mContactAdapter == null) {
            mContactAdapter = new ContactAdapter();
        }
        mContactLayout.setAdapter(mContactAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContactEvent(OnContactUpdateEvent onContactUpdateEvent) {
        mPresenter.goToRefresh();
    }

    @Override
    public void fillData(List<String> contactsList) {
        mContactAdapter.setData(contactsList);

        mContactAdapter.setOnItemLongClickListener(this);
    }

    @Override
    public void upDateContacts(boolean isSuccess, String msg, List<String> contactsList) {

        if (isSuccess) {
            if (mContactAdapter != null) {
                mContactAdapter.setData(contactsList);
            }
        } else {
            ToastUtils.initToast(msg);
        }

        mContactLayout.setSwipeRefreshing(false);
    }

    @Override
    public void delectContanct(boolean isSuccess, String msg) {
        if (isSuccess) {
            ToastUtils.initToast("删除成功");
        } else {
            ToastUtils.initToast("删除失败:" + msg);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.goToRefresh();
    }

    /**
     * Item长按回调
     *
     * @param contact
     * @param position
     */
    @Override
    public void onItemLongClick(final String contact, int position) {
        Snackbar.make(mContactLayout, "确定删除好友" + contact + "嘛？", Snackbar.LENGTH_LONG)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.goToDelect(contact);
                    }
                }).show();
    }

    /**
     * Item点击回调
     *
     * @param contact
     * @param position
     */
    @Override
    public void onItemClick(String contact, int position) {
        mPresenter.goToChat(contact);
    }
}

package com.jiayang.imdemo.v.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.bean.User;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.activity.AddFriendActivityPst;
import com.jiayang.imdemo.utils.CommonUtil;
import com.jiayang.imdemo.utils.ToastUtils;
import com.jiayang.imdemo.v.adapter.AddFriendsAdapter;
import com.jiayang.imdemo.v.base.BaseActivity;
import com.jiayang.imdemo.v.iview.IaddFriendActivityView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 张 奎 on 2017-10-13 08:14.
 */

public class AddFriendActivity extends BaseActivity<AddFriendActivityPst> implements IaddFriendActivityView, SearchView.OnQueryTextListener, AddFriendsAdapter.OnAddFriendClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_nodata)
    ImageView mIvNodata;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    private SearchView mSearchView;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addfriend;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initShow();
    }

    private void initShow() {
        mToolbar.setTitle("搜好友");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addfriend, menu);
        /**
         * 初始化菜单中的SearchView
         */
        MenuItem menuItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) menuItem.getActionView();
        /**
         * 在SearchView中添加提示
         */
        mSearchView.setQueryHint("搜好友");
        mSearchView.setOnQueryTextListener(this);

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }


        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        if (TextUtils.isEmpty(query)) {
            ToastUtils.initToast("请输入用户名再搜索！");
            return false;
        }

        mPresenter.goToSearchFriend(query);
        CommonUtil.hideSoftKeyboard(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onSearchResult(List<User> userList, List<String> contactsList, boolean success, String msg) {
        if (success) {
            mIvNodata.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            AddFriendsAdapter mAdapter = new AddFriendsAdapter(userList, contactsList);
            mAdapter.setOnAddFriendClickListener(this);
            mRecyclerView.setAdapter(mAdapter);


        } else {
            mIvNodata.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAddResult(String username, boolean success, String msg) {
        Snackbar.make(mToolbar, "添加好友" + username + (success ? "成功" : "失败:" + msg), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onAddClick(String username) {
        mPresenter.goToAddFriend(username);
    }
}

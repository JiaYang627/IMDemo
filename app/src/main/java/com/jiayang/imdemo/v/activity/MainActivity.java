package com.jiayang.imdemo.v.activity;

import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.activity.MainActivityPst;
import com.jiayang.imdemo.utils.ToastUtils;
import com.jiayang.imdemo.v.base.BaseActivity;
import com.jiayang.imdemo.v.iview.ImainActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainActivityPst> implements ImainActivityView, BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolBar();
        initBottomNavigation();
    }

    private void initToolBar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mTvTitle.setText("消息");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initBottomNavigation() {
        BottomNavigationItem conversationItem = new BottomNavigationItem(R.mipmap.conversation_selected_2, getString(R.string.main_conversation));
        BottomNavigationItem contactItem = new BottomNavigationItem(R.mipmap.contact_selected_2, getString(R.string.main_contact));
        BottomNavigationItem pluginItem = new BottomNavigationItem(R.mipmap.plugin_selected_2, getString(R.string.main_plugin));


        mBottomNavigationBar.addItem(conversationItem);
        mBottomNavigationBar.addItem(contactItem);
        mBottomNavigationBar.addItem(pluginItem);

        mBottomNavigationBar.setActiveColor(R.color.btn_normal);
        mBottomNavigationBar.setInActiveColor(R.color.inActive);

        mBottomNavigationBar.initialise();
        mBottomNavigationBar.setTabSelectedListener(this);

    }

    /**
     * 加载右上角菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 让菜单左侧的图片显示出来
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuBuilder builder = (MenuBuilder) menu;
        builder.setOptionalIconsVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_friend:
                ToastUtils.initToast("点击了 添加好友按钮");
                //TODO 跳转添加好友
                break;
            case R.id.menu_scan:
                ToastUtils.initToast("点击了 扫一扫按钮");
                break;
            case R.id.menu_about:
                ToastUtils.initToast("点击了 关于我们按钮");
                break;
            case android.R.id.home:
                ToastUtils.initToast("点击了 侧滑菜单按钮");
                break;
        }
        return true;
    }

    // 底部按钮点击事件监听。
    @Override
    public void onTabSelected(int position) {

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}

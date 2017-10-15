package com.jiayang.imdemo.p.activity;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jiayang.imdemo.db.DBUtils;
import com.jiayang.imdemo.m.bean.User;
import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.utils.ThreadUtils;
import com.jiayang.imdemo.v.iview.IaddFriendActivityView;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 张 奎 on 2017-10-13 08:15.
 */

public class AddFriendActivityPst extends BasePresenter<IaddFriendActivityView> {


    private IMService mIMService;

    @Inject
    public AddFriendActivityPst(ErrorListener errorListener , IMService imService) {
        super(errorListener);

        mIMService = imService;
    }

    public void goToSearchFriend(String key) {

        final String currentUser = EMClient.getInstance().getCurrentUser();
        BmobQuery<User> query = new BmobQuery<>();

        query.addWhereStartsWith("username", key)
                .addWhereNotEqualTo("username" , currentUser)
                .findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null && list != null && list.size() > 0) {
                            List<String> contacts = DBUtils.getContacts(currentUser);
                            //获取到数据
                            mView.onSearchResult(list, contacts, true, null);
                        } else {
                            //没有找到数据
                            if (e == null) {
                                mView.onSearchResult(null, null, false, "没有找到对应的用户。");
                            } else {
                                mView.onSearchResult(null, null, false, e.getMessage());
                            }
                        }
                    }
                });

    }

    public void goToAddFriend(final String username) {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(username,"想加您为好友，一起写代码！");
                    //添加成功（仅仅代表这个请求发送成功了，对方有没有同意是另外一会儿事儿）
                    onAddResult(username,true,null);
                } catch (HyphenateException e) {
                    //添加失败
                    e.printStackTrace();
                    onAddResult(username,false,e.getMessage());

                }
            }
        });
    }
    private void onAddResult(final String username, final boolean success, final String msg) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mView.onAddResult(username, success, msg);
            }
        });
    }
}

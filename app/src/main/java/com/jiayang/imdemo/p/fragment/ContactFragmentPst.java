package com.jiayang.imdemo.p.fragment;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jiayang.imdemo.db.DBUtils;
import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.utils.ThreadUtils;
import com.jiayang.imdemo.v.iview.IcontactFragmentView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-10-02 14:50.
 */

public class ContactFragmentPst extends BasePresenter<IcontactFragmentView> {

    private IMService mIMService;

    private List<String> contactsList = new ArrayList<>();

    @Inject
    public ContactFragmentPst(ErrorListener errorListener, IMService imService) {
        super(errorListener);
        mIMService = imService;
    }


    @Override
    public void onOnceTakeView() {
        super.onOnceTakeView();
        /**
         *  1.先访问本地缓存的联系人。
         *  2.开子线程去环信后台获取当前用户的联系人。
         *  3.更新本地缓存，更新UI。
         */
        final String currentUser = EMClient.getInstance().getCurrentUser();
        List<String> contacts = DBUtils.getContacts(currentUser);
        contactsList.clear();
        contactsList.addAll(contacts);

        mView.fillData(contactsList);

        // 开子线程去环信后台获取当前用户的联系人。

        goToGetContacts(currentUser);


    }

    private void goToGetContacts(final String currentUser) {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> contactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    // 排序
                    Collections.sort(contactsFromServer, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);// 正序
                        }
                    });
                    // 更新本地缓存
                    DBUtils.updateContacts(currentUser, contactsFromServer);
                    contactsList.clear();
                    contactsList.addAll(contactsFromServer);
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.upDateContacts(true, null);
                        }
                    });


                } catch (final HyphenateException e) {
                    e.printStackTrace();

                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.upDateContacts(false, e.getMessage());
                        }
                    });
                }
            }
        });
    }

    public void goToRefresh() {
        goToGetContacts(EMClient.getInstance().getCurrentUser());
    }

    public void goToDelect(final String contact) {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(contact);
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.delectContanct(true, null);
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    mView.delectContanct(false, e.toString());

                }
            }
        });
    }
}

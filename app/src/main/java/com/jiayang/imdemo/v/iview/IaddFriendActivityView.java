package com.jiayang.imdemo.v.iview;

import com.jiayang.imdemo.m.bean.User;
import com.jiayang.imdemo.v.base.IBaseView;

import java.util.List;

/**
 * Created by 张 奎 on 2017-10-13 08:15.
 */

public interface IaddFriendActivityView extends IBaseView {

    void onSearchResult(List<User> userList, List<String> contactsList, boolean success, String msg);

    void onAddResult(String username, boolean success, String msg);
}

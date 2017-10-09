package com.jiayang.imdemo.v.iview;

import com.jiayang.imdemo.v.base.IBaseView;

import java.util.List;

/**
 * Created by 张 奎 on 2017-10-02 14:51.
 */

public interface IcontactFragmentView extends IBaseView {
    void fillData(List<String> contactsList);

    void upDateContacts(boolean isSuccess, String msg);
}

package com.jiayang.imdemo.v.iview;

import com.jiayang.imdemo.v.base.IBaseView;

/**
 * Created by 张 奎 on 2017-10-02 14:53.
 */

public interface IpluginFragmentView extends IBaseView {
    void fillData(String currentUser, boolean isSuccess, String msg);
}

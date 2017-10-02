package com.jiayang.imdemo.v.iview;

import com.jiayang.imdemo.v.base.IBaseView;

/**
 * Created by 张 奎 on 2017-10-01 10:41.
 */

public interface IloginActivityView extends IBaseView {
    void fillData(String userName, String userPwd, boolean isSuccess, String msg);
}

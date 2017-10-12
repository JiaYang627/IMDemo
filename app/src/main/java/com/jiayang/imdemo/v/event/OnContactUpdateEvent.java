package com.jiayang.imdemo.v.event;

/**
 * Created by 张 奎 on 2017-10-12 09:27.
 */

public class OnContactUpdateEvent {
    public String contactName;
    public boolean isAdd;

    public OnContactUpdateEvent(String contactName, boolean isAdd) {
        this.contactName = contactName;
        this.isAdd = isAdd;
    }
}

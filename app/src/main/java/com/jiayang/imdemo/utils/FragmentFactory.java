package com.jiayang.imdemo.utils;


import com.jiayang.imdemo.v.base.BaseFragment;
import com.jiayang.imdemo.v.fragment.ContactFragment;
import com.jiayang.imdemo.v.fragment.ConversationFragment;
import com.jiayang.imdemo.v.fragment.PluginFragment;


/**
 *
 */

public class FragmentFactory {

    private static ConversationFragment sConversationFragment;
    private static ContactFragment sContactFragment;
    private static PluginFragment sPluginFragment;

    public static BaseFragment getFragment(int position){
        BaseFragment baseFragment = null;
        switch (position) {
            case 0:
                if (sConversationFragment==null){
                    sConversationFragment = new ConversationFragment();
                }
                baseFragment = sConversationFragment;
                break;
            case 1:
                if (sContactFragment==null){
                    sContactFragment = new ContactFragment();
                }
                baseFragment = sContactFragment;
                break;
            case 2:
                if (sPluginFragment==null){
                    sPluginFragment = new PluginFragment();
                }
                baseFragment = sPluginFragment;
                break;
        }
        return baseFragment;

    }

}

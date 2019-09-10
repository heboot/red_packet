package com.zonghong.redpacket;

import android.net.Uri;
import android.support.v4.app.FragmentManager;


import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityMainBinding;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import me.yokeyword.fragmentation.ISupportFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {


    private ConversationListFragment conversationListFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {
        //会话列表
        FragmentManager fragmentManage = getSupportFragmentManager();
        conversationListFragment = (ConversationListFragment) fragmentManage.findFragmentById(R.id.flyt_container);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        conversationListFragment.setUri(uri);

        mDelegate.loadMultipleRootFragment(binding.flytContainer.getId(), 0, (ISupportFragment) conversationListFragment);


    }

    @Override
    public void initListener() {

    }
}

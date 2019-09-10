package com.zonghong.redpacket.fragment;

import android.net.Uri;
import android.os.Bundle;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.databinding.FragmentConversationBinding;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MConversationListFragment extends BaseFragment<FragmentConversationBinding> {

    private ConversationListFragment conversationListFragment;

    public static MConversationListFragment newInstance() {
        Bundle args = new Bundle();
        MConversationListFragment fragment = new MConversationListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_conversation;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {
        if (conversationListFragment == null) {
//            conversationListFragment = (ConversationListFragment) fragmentManage.findFragmentById(R.id.subconversationlist);
            conversationListFragment = new ConversationListFragment();
        }

        Uri uri = Uri.parse("rong://" + _mActivity.getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        conversationListFragment.setUri(uri);
//        getFragmentManager().beginTransaction().show(conversationListFragment);
        getFragmentManager().beginTransaction().add(R.id.container, conversationListFragment).commit();
//        mDelegate.loadRootFragment(R.id.container, conversationListFragment);
    }

    @Override
    public void initListener() {

    }
}

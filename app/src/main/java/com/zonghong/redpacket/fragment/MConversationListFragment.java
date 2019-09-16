package com.zonghong.redpacket.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ChooseContactsActivity;
import com.zonghong.redpacket.activity.contacts.SearchContactsActivity;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.databinding.FragmentConversationBinding;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.view.MsgMorePopView;

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
        if (conversationListFragment == null) {
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
        getFragmentManager().beginTransaction().add(R.id.llyt_container, conversationListFragment).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.tvRight.setOnClickListener((v) -> {
            if (binding.includeMsgMore.getRoot().getVisibility() == View.GONE) {
                binding.includeMsgMore.getRoot().setVisibility(getView().VISIBLE);
            } else {
                binding.includeMsgMore.getRoot().setVisibility(getView().GONE);
            }
//            MsgMorePopView msgMorePopView = new MsgMorePopView();
//            msgMorePopView.showAsDropDown(v);
        });
        binding.includeMsgMore.llytContainer.setOnClickListener((v) -> {
            binding.includeMsgMore.getRoot().setVisibility(getView().GONE);
        });
        binding.includeMsgMore.tvNewGroup.setOnClickListener((v) -> {
            IntentUtils.doIntent(ChooseContactsActivity.class);
            binding.includeMsgMore.getRoot().setVisibility(getView().GONE);
        });
        binding.includeSearch.getRoot().setOnClickListener((v) -> {
            IntentUtils.doIntent(SearchContactsActivity.class);
            binding.includeMsgMore.getRoot().setVisibility(getView().GONE);
        });
        binding.includeMsgMore.tvAddFriend.setOnClickListener((v) -> {
            IntentUtils.doIntent(SearchContactsActivity.class);
            binding.includeMsgMore.getRoot().setVisibility(getView().GONE);
        });
    }
}

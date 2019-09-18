package com.zonghong.redpacket.activity.chat;

import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityChatDetailBinding;

public class ChatDetailActivity extends BaseActivity<ActivityChatDetailBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_detail;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("聊天详情");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}

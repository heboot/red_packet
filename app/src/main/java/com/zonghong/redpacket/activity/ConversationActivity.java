package com.zonghong.redpacket.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ConversationBinding;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

public class ConversationActivity extends BaseActivity<ConversationBinding> {

    private String mTargetId; //目标 Id
    private Conversation.ConversationType mConversationType; //会话类型
    private String title;

    @Override
    protected int getLayoutId() {
        return R.layout.conversation;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
//        type = (Conversation.ConversationType) getIntent().getExtras().get(MKey.TYPE);
//        targetId = (String) getIntent().getExtras().get(MKey.ID);

        /* 从 intent 携带的数据里获取 targetId 和会话类型*/
        Intent intent = getIntent();
        mTargetId = intent.getData().getQueryParameter("targetId");
        title = intent.getData().getQueryParameter("title");
        binding.includeToolbar.tvTitle.setText(title);
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase());


        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragement.setUri(uri);
    }

    @Override
    public void initListener() {

    }
}

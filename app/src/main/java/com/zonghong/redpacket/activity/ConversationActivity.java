package com.zonghong.redpacket.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.event.GroupEvent;
import com.waw.hr.mutils.event.MessageEvent;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ConversationBinding;
import com.zonghong.redpacket.fragment.MConversationFragment;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class ConversationActivity extends BaseActivity<ConversationBinding> {

    private String mTargetId; //目标 Id
    private Conversation.ConversationType mConversationType; //会话类型
    private String title;

    private MConversationFragment conversationFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.conversation;
    }

    @Override
    public void initUI() {
        EventBus.getDefault().register(this);
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
        binding.tvTitle.setText(title);
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase());
        if (mConversationType == Conversation.ConversationType.PRIVATE) {
            binding.tvRight.setVisibility(View.GONE);
            binding.tvRightIc.setVisibility(View.GONE);
//            RongUtils.setPrivateExtensionModule();
        } else {
//            RongUtils.setGroupExtensionModule();
        }
        FragmentManager fragmentManage = getSupportFragmentManager();
        conversationFragment = (MConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        conversationFragment.setUri(uri);
    }

    @Override
    public void initListener() {
        binding.tvRight.setOnClickListener((v) -> {
            if (mConversationType == Conversation.ConversationType.GROUP) {
                IntentUtils.intent2GroupDetailActivity(mTargetId);
            } else {
                IntentUtils.intent2ChatDetailActivity(mTargetId);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        RongUtils.checkUnread();
    }

    @Override
    protected void onStop() {
        RongUtils.checkUnread();
        super.onStop();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GroupEvent.EXIT_GROUP_EVENT event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GroupEvent.DELETE_GROUP_MESSAGE_EVENT event) {
        RongIMClient.getInstance().deleteMessages(Conversation.ConversationType.GROUP, event.getGroupId(), new RongIMClient.ResultCallback<Boolean>() {
            //            @Override
            public void onSuccess(Boolean aBoolean) {
                conversationFragment.getMessageAdapter().clear();
                conversationFragment.getMessageAdapter().notifyDataSetChanged();

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                ToastUtils.show(MAPP.mapp, JSON.toJSONString(errorCode));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent.SEND_CUSTOM_BIAOQING_EVENT event) {
       RongUtils.sendCustonBiaoqingMessage(mTargetId,mConversationType,event.getExpressionListBean().getImage());
    }



}



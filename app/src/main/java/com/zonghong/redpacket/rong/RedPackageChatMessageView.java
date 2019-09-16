package com.zonghong.redpacket.rong;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.MessageRedpackageChatBinding;
import com.zonghong.redpacket.view.RedPackageDialog;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

@ProviderTag(messageContent = RedPackageChatMessage.class, showReadState = true)
public class RedPackageChatMessageView extends IContainerItemProvider.MessageProvider<RedPackageChatMessage> {


    @Override
    public void bindView(View view, int i, RedPackageChatMessage messageContent, UIMessage uiMessage) {
        MessageRedpackageChatBinding messageRedpackageChatBinding = DataBindingUtil.bind(view);
        String s = new String(messageContent.encode());
        CreateRedPackageChildBean createRedPackageChildBean = JSON.parseObject(s, CreateRedPackageChildBean.class);
        messageRedpackageChatBinding.tvDes.setText(createRedPackageChildBean.getDesc());
    }

    @Override
    public Spannable getContentSummary(RedPackageChatMessage messageContent) {
        return new SpannableString("[红包消息]");
    }

    @Override
    public void onItemClick(View view, int i, RedPackageChatMessage messageContent, UIMessage uiMessage) {
        // TODO: 2019-09-11 抢红包
        String s = new String(messageContent.encode());
        CreateRedPackageChildBean createRedPackageChildBean = JSON.parseObject(s, CreateRedPackageChildBean.class);
        RedPackageDialog redPackageDialog = RedPackageDialog.newInstance(String.valueOf(createRedPackageChildBean.getID()), uiMessage.getConversationType(), createRedPackageChildBean.getFrom_id(), createRedPackageChildBean);
        redPackageDialog.show(((FragmentActivity) MAPP.mapp.getCurrentActivity()).getSupportFragmentManager(), "");
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_redpackage_chat, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.x231), MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.y90)));
        return view;
    }


}

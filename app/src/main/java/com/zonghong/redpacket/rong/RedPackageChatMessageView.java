package com.zonghong.redpacket.rong;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zonghong.redpacket.R;

import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.MessageContent;

public class RedPackageChatMessageView extends IContainerItemProvider.MessageProvider {


    @Override
    public void bindView(View view, int i, MessageContent messageContent, UIMessage uiMessage) {

    }

    @Override
    public Spannable getContentSummary(MessageContent messageContent) {
        return new SpannableString("[红包消息]");
    }

    @Override
    public void onItemClick(View view, int i, MessageContent messageContent, UIMessage uiMessage) {
        // TODO: 2019-09-11 抢红包

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_redpackage_chat, null);
        return view;
    }

    @Override
    public void bindView(View view, int i, Object o) {

    }
}

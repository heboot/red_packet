package com.zonghong.redpacket.rong;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.view.RedPackageDialog;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

@ProviderTag(messageContent = RedPackageChatOpenMessage.class, showSummaryWithName = false, showPortrait = false, centerInHorizontal = true, showReadState = true)
public class RedPackageChatTipMessageView extends IContainerItemProvider.MessageProvider<RedPackageChatOpenMessage> {


    @Override
    public void bindView(View view, int i, RedPackageChatOpenMessage messageContent, UIMessage uiMessage) {
//        LogUtil.e("收到提示消息", uiMessage.getUserInfo().getName());
    }

    @Override
    public Spannable getContentSummary(RedPackageChatOpenMessage messageContent) {
        return new SpannableString("[红包消息]");
    }

    @Override
    public void onItemClick(View view, int i, RedPackageChatOpenMessage messageContent, UIMessage uiMessage) {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_redpackage_open, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.y25)));
        return view;
    }


}

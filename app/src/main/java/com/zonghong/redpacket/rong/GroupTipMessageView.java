package com.zonghong.redpacket.rong;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.bean.CustomBiaoqingBean;
import com.waw.hr.mutils.bean.RedpackageTipBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.MessageCustomBiaoqingBinding;
import com.zonghong.redpacket.databinding.MessageGroupTipBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

@ProviderTag(
        messageContent = GroupTipEventMessage.class,
          showSummaryWithName = false, showPortrait = false, centerInHorizontal = true, showReadState = true
)
public class GroupTipMessageView extends IContainerItemProvider.MessageProvider<GroupTipEventMessage> {

    private static final String TAG = "ImageMessageItemProvider";

    public GroupTipMessageView() {
    }

    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_group_tip, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }

    public void bindView(View v, int position, GroupTipEventMessage content, UIMessage message) {
        MessageGroupTipBinding binding = DataBindingUtil.bind(v);
        binding.rcMsg.setText(content.getContent());
    }

    @Override
    public Spannable getContentSummary(GroupTipEventMessage groupTipEventMessage) {
        return new SpannableString("[提示]");
    }

    @Override
    public void onItemClick(View view, int i, GroupTipEventMessage groupTipEventMessage, UIMessage uiMessage) {

    }


    public Spannable getContentSummary(Context context, CustomBiaoqingMessage data) {
        return new SpannableString("[提示]");
    }


}

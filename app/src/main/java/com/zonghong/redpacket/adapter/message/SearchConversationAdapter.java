package com.zonghong.redpacket.adapter.message;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.StringUtils;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.common.SearchMessageType;
import com.zonghong.redpacket.databinding.ItemFindConversationBinding;
import com.zonghong.redpacket.databinding.ItemFindMessageBinding;
import com.zonghong.redpacket.rong.CustomBiaoqingMessage;
import com.zonghong.redpacket.rong.RedPackageChatMessage;
import com.zonghong.redpacket.rong.RedPackageChatOpenMessage;
import com.zonghong.redpacket.rong.RedPackageZhuanZhangChatMessage;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.utils.ImageUtils;

import java.util.List;

import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

public class SearchConversationAdapter extends BaseQuickAdapter<Conversation, BaseViewHolder> {

    private SearchMessageType searchMessageType;

    private String targetId, targetName;

    // type 0 红包  1 转账  2 充值  3  提现
    public SearchConversationAdapter(@Nullable List<Conversation> data) {
        super(R.layout.item_find_conversation, data);
        this.searchMessageType = searchMessageType;
        this.targetId = targetId;
        this.targetName = targetName;
    }


    @Override
    protected void convert(BaseViewHolder helper, Conversation item) {
        ItemFindConversationBinding binding = DataBindingUtil.bind(helper.itemView);
        String content;
        MessageContent message = item.getLatestMessage();
        if (message instanceof ImageMessage) {
            content = "[图片消息]";
        } else if (message instanceof VoiceMessage) {
            content = "[语音消息]";
        } else if (message instanceof TextMessage) {
            content = new TextMessage(message.encode()).getContent();
        } else if (message instanceof RedPackageChatOpenMessage || message instanceof RedPackageChatMessage) {
            content = "[红包消息]";
        } else if (message instanceof RedPackageZhuanZhangChatMessage) {
            content = "[转账消息]";
        } else if (message instanceof CustomBiaoqingMessage) {
            content = "[表情]";
        } else {
            content = "";
        }
        binding.tvName.setText(item.getConversationTitle());
        ImageUtils.showAvatar(item.getPortraitUrl(), binding.ivAvatar);
        binding.tvContent.setText(content);


        binding.getRoot().setOnClickListener((v) -> {
            if (item.getConversationType() == Conversation.ConversationType.PRIVATE) {
                RongUtils.toChat(item.getTargetId(), item.getConversationTitle());
            } else if (item.getConversationType() == Conversation.ConversationType.GROUP) {
                RongUtils.toGroupChat(item.getTargetId(), item.getConversationTitle());
            }
        });
    }
}
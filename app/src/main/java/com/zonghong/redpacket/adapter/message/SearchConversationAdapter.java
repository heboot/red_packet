package com.zonghong.redpacket.adapter.message;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.common.SearchMessageType;
import com.zonghong.redpacket.databinding.ItemFindConversationBinding;
import com.zonghong.redpacket.databinding.ItemFindMessageBinding;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.utils.ImageUtils;

import java.util.List;

import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

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
        binding.tvName.setText(item.getConversationTitle());
        ImageUtils.showAvatar(item.getPortraitUrl(), binding.ivAvatar);
        binding.tvContent.setText(String.valueOf(new TextMessage(item.getLatestMessage().encode()).getContent()));


        binding.getRoot().setOnClickListener((v) -> {
            if (item.getConversationType() == Conversation.ConversationType.PRIVATE) {
                RongUtils.toChatByTime(item.getTargetId(), item.getConversationTitle(), item.getSentTime());
            } else if (item.getConversationType() == Conversation.ConversationType.GROUP) {
                RongUtils.toGroupChat(item.getTargetId(), item.getConversationTitle(), item.getSentTime());
            }
        });
    }
}
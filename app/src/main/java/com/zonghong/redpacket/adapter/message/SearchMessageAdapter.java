package com.zonghong.redpacket.adapter.message;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.common.SearchMessageType;
import com.zonghong.redpacket.databinding.ItemFindMessageBinding;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.utils.ImageUtils;

import java.util.List;

import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class SearchMessageAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {

    private SearchMessageType searchMessageType;

    private String targetId, targetName;

    // type 0 红包  1 转账  2 充值  3  提现
    public SearchMessageAdapter(@Nullable List<Message> data, SearchMessageType searchMessageType, String targetId, String targetName) {
        super(R.layout.item_find_message, data);
        this.searchMessageType = searchMessageType;
        this.targetId = targetId;
        this.targetName = targetName;
    }


    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        ItemFindMessageBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvName.setText(item.getContent().getUserInfo().getName());
        ImageUtils.showAvatar(item.getContent().getUserInfo().getPortraitUri().toString(), binding.ivAvatar);
        binding.tvContent.setText(String.valueOf(new TextMessage(item.getContent().encode()).getContent()));
        binding.getRoot().setOnClickListener((v) -> {
            if (searchMessageType == SearchMessageType.USER) {
                RongUtils.toChatByTime(targetId, targetName, item.getSentTime());
            } else if (searchMessageType == SearchMessageType.GROUP) {
                RongUtils.toGroupChat(targetId, targetName, item.getSentTime());
            }
        });
    }
}
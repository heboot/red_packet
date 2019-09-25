package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.BlackListBean;
import com.waw.hr.mutils.bean.ContatsFriendBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.ItemContactsBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.List;

public class BlackUserAdapter extends BaseQuickAdapter<BlackListBean, BaseViewHolder> {


    public BlackUserAdapter(@Nullable List<BlackListBean> data) {
        super(R.layout.item_contacts, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, BlackListBean item) {
        ItemContactsBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.cb.setVisibility(View.GONE);

        binding.tvName.setText(item.getNick_name());
        ImageUtils.showAvatar(item.getImage(), binding.ivAvatar);

//        if (!isChoose) {
        binding.getRoot().setOnClickListener((v) -> {
//                RongUtils.toChat(String.valueOf(item.getFriend_id()), item.getReal_name());
            IntentUtils.intent2ContactsDetailActivity(item.getFriend_id() + "", ContactsDetailType.NORMAL);
        });
//        }


    }
}

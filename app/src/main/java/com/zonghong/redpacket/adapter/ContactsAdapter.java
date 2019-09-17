package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.ContatsFriendBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.ItemContactsBinding;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.utils.ImageUtils;

import java.util.List;

public class ContactsAdapter extends BaseQuickAdapter<ContatsFriendBean, BaseViewHolder> {

    private boolean isChoose = false;

    public ContactsAdapter(@Nullable List<ContatsFriendBean> data) {
        super(R.layout.item_contacts, data);
    }

    public ContactsAdapter(@Nullable List<ContatsFriendBean> data, boolean isChoose) {
        super(R.layout.item_contacts, data);
        this.isChoose = isChoose;
    }


    @Override
    protected void convert(BaseViewHolder helper, ContatsFriendBean item) {
        ItemContactsBinding binding = DataBindingUtil.bind(helper.itemView);
        if (isChoose) {
            binding.cb.setVisibility(View.VISIBLE);
        } else {
            binding.cb.setVisibility(View.GONE);
        }

        binding.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    item.setCheck(true);
                } else {
                    item.setCheck(false);
                }
            }
        });

        binding.tvName.setText(item.getReal_name());
        ImageUtils.showAvatar(item.getImage(), binding.ivAvatar);

        if (!isChoose) {
            binding.getRoot().setOnClickListener((v) -> {
                RongUtils.toChat(String.valueOf(item.getID()), item.getReal_name());
            });
        }


    }
}

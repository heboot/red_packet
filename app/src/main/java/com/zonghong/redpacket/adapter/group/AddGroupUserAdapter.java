package com.zonghong.redpacket.adapter.group;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.ContatsFriendBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.ItemContactsBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.List;

public class AddGroupUserAdapter extends BaseQuickAdapter<ContatsFriendBean, BaseViewHolder> {

    private boolean isChoose = false;

    public AddGroupUserAdapter(@Nullable List<ContatsFriendBean> data) {
        super(R.layout.item_contacts, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ContatsFriendBean item) {
        ItemContactsBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.cb.setVisibility(View.VISIBLE);

        if (item.getExist() == 0) {
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
        } else {
            binding.cb.setOnClickListener(null);
        }


        if (item.getExist() == 1) {
            binding.cb.setChecked(true);
        } else {
            binding.cb.setChecked(false);
        }

        binding.tvName.setText(item.getNick_name());
        ImageUtils.showAvatar(item.getImage(), binding.ivAvatar);

//        if (!isChoose) {
//            binding.getRoot().setOnClickListener((v) -> {
////                RongUtils.toChat(String.valueOf(item.getFriend_id()), item.getReal_name());
//                IntentUtils.intent2ContactsDetailActivity(item.getFriend_id() + "", ContactsDetailType.NORMAL);
//            });
//        }


    }
}

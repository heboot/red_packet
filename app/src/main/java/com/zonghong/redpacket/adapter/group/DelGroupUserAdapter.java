package com.zonghong.redpacket.adapter.group;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.bean.GroupUserListBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.ItemContactsBinding;
import com.zonghong.redpacket.utils.ImageUtils;

import java.util.List;

public class DelGroupUserAdapter extends BaseQuickAdapter<GroupUserListBean.GUserBean, BaseViewHolder> {


    public DelGroupUserAdapter(@Nullable List<GroupUserListBean.GUserBean> data) {
        super(R.layout.item_contacts, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, GroupUserListBean.GUserBean item) {
        ItemContactsBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.cb.setVisibility(View.VISIBLE);
        binding.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (item.getAdmin() == 3) {
                    ToastUtils.show(MAPP.mapp, "不能删除群主");
                    return;
                }
                if (b) {
                    item.setCheck(true);
                } else {
                    item.setCheck(false);
                }
            }
        });
        if (item.getAdmin() == 3) {
            binding.cb.setEnabled(false);
        } else {
            binding.cb.setEnabled(true);
        }
        binding.tvName.setText(item.getNick_name());
        ImageUtils.showAvatar(item.getImage(), binding.ivAvatar);

//        if (!isChoose) {
//            binding.getRoot().setOnClickListener((v) -> {
////                RongUtils.toChat(String.valueOf(item.getFriend_id()), item.getReal_name());
//                SearchContatsBean searchContatsBean = new SearchContatsBean();
//                searchContatsBean.setID(String.valueOf(item.getUser_id()));
//                IntentUtils.intent2ContactsDetailActivity(searchContatsBean);
//            });
//        }


    }
}

package com.zonghong.redpacket.adapter.group;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.GroupInfoBean;
import com.waw.hr.mutils.bean.MyGroupBean;
import com.waw.hr.mutils.bean.NewFriendListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ContactsNewListActivity;
import com.zonghong.redpacket.databinding.ItemNewFriendBinding;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.utils.ImageUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class MyGroupAdapter extends BaseQuickAdapter<MyGroupBean, BaseViewHolder> {


    public MyGroupAdapter(@Nullable List<MyGroupBean> data) {
        super(R.layout.item_new_friend, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, MyGroupBean item) {
        ItemNewFriendBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvName.setText(item.getTitle());
        ImageUtils.showAvatar(item.getImg(), binding.ivAvatar);
        binding.tvOption.setVisibility(View.GONE);
        binding.getRoot().setOnClickListener((v) -> {
            RongUtils.toGroupChat(String.valueOf(item.getGroup_id()), item.getTitle());
        });
    }
}

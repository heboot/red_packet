package com.zonghong.redpacket.adapter.group;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.waw.hr.mutils.bean.NewFriendListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ContactsNewListActivity;
import com.zonghong.redpacket.databinding.ItemGroupUserBinding;
import com.zonghong.redpacket.databinding.ItemNewFriendBinding;
import com.zonghong.redpacket.utils.ImageUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class GroupUserAdapter extends BaseQuickAdapter<GroupDetaiInfoBean.GroupUserInfoBean, BaseViewHolder> {


    public GroupUserAdapter(@Nullable List<GroupDetaiInfoBean.GroupUserInfoBean> data) {
        super(R.layout.item_group_user, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, GroupDetaiInfoBean.GroupUserInfoBean item) {
        ItemGroupUserBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvName.setText(item.getGroup_name());
        ImageUtils.showAvatar(item.getImage(), binding.ivAvatar);
    }
}

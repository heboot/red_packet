package com.zonghong.redpacket.adapter.group;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.waw.hr.mutils.bean.NewFriendListBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ContactsNewListActivity;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.ItemGroupUserBinding;
import com.zonghong.redpacket.databinding.ItemNewFriendBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class GroupUserAdapter extends BaseQuickAdapter<GroupDetaiInfoBean.GroupUserInfoBean, BaseViewHolder> {

    private String groupId;

    private int admin;

    public GroupUserAdapter(@Nullable List<GroupDetaiInfoBean.GroupUserInfoBean> data, String groupId, int admin) {
        super(R.layout.item_group_user, data);
        this.groupId = groupId;
        this.admin = admin;
    }


    @Override
    protected void convert(BaseViewHolder helper, GroupDetaiInfoBean.GroupUserInfoBean item) {
        ItemGroupUserBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvName.setText(item.getGroup_name());
        if (!StringUtils.isEmpty(item.getOption()) && item.getOption().equals("jia")) {
            ImageUtils.showImage(R.mipmap.icon_groupjia, binding.ivAvatar);
        } else if (!StringUtils.isEmpty(item.getOption()) && item.getOption().equals("jian")) {
            ImageUtils.showImage(R.mipmap.icon_groupjian, binding.ivAvatar);
        } else {
            ImageUtils.showAvatar(item.getImage(), binding.ivAvatar);
        }

        binding.getRoot().setOnClickListener(view -> {
            if (!StringUtils.isEmpty(item.getOption()) && item.getOption().equals("jia")) {
                IntentUtils.intent2AddGroupUserActivtiy(groupId);
            } else if (!StringUtils.isEmpty(item.getOption()) && item.getOption().equals("jian")) {
                IntentUtils.intent2DelGroupUserActivtiy(groupId);
            } else {
                IntentUtils.intent2ContactsDetailActivity(item.getUser_id() + "", groupId, admin, ContactsDetailType.GROUP );
            }
        });

    }
}

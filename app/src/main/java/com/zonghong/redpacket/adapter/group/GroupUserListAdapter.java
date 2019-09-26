package com.zonghong.redpacket.adapter.group;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.waw.hr.mutils.bean.GroupUserListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.ItemGroupUserBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.List;

public class GroupUserListAdapter extends BaseQuickAdapter<GroupUserListBean.GUserBean, BaseViewHolder> {

    private String groupId;

    private int admin;

    public GroupUserListAdapter(@Nullable List<GroupUserListBean.GUserBean> data, String groupId, int admin) {
        super(R.layout.item_group_user, data);
        this.groupId = groupId;
        this.admin = admin;
    }


    @Override
    protected void convert(BaseViewHolder helper, GroupUserListBean.GUserBean item) {
        ItemGroupUserBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvName.setText(item.getNick_name());

        binding.getRoot().setOnClickListener(view -> {
            IntentUtils.intent2ContactsDetailActivity(item.getUser_id() + "", groupId, admin > 1 ? ContactsDetailType.GROUP : ContactsDetailType.NORMAL);
        });

    }
}

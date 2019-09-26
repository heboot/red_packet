package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.NewFriendListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ContactsNewListActivity;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.ItemNewFriendBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class NewContactsAdapter extends BaseQuickAdapter<NewFriendListBean, BaseViewHolder> {

    private WeakReference<ContactsNewListActivity> weakReference;

    public NewContactsAdapter(@Nullable List<NewFriendListBean> data, WeakReference<ContactsNewListActivity> weakReference) {
        super(R.layout.item_new_friend, data);
        this.weakReference = weakReference;
    }


    @Override
    protected void convert(BaseViewHolder helper, NewFriendListBean item) {
        ItemNewFriendBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvName.setText(item.getFriend_name());
        ImageUtils.showAvatar(item.getFriend_image(), binding.ivAvatar);
        binding.tvOption.setOnClickListener((v) -> {
            weakReference.get().apply(String.valueOf(item.getMy_id()));
        });
//        binding.getRoot().setOnClickListener((v) -> {
//            IntentUtils.intent2ContactsDetailActivity(item.getMy_id() + "", ContactsDetailType.NORMAL);
//        });
    }
}

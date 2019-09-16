package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.NewFriendListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ContactsNewListActivity;
import com.zonghong.redpacket.databinding.ItemNewFriendBinding;
import com.zonghong.redpacket.utils.ImageUtils;

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
        binding.tvName.setText(item.getReal_name());
        ImageUtils.showAvatar(item.getImage(), binding.ivAvatar);
        binding.tvOption.setOnClickListener((v) -> {
            weakReference.get().apply(item.getID());
        });
    }
}

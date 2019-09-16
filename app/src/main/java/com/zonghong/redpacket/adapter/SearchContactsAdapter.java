package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.NewFriendListBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ContactsNewListActivity;
import com.zonghong.redpacket.databinding.ItemNewFriendBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class SearchContactsAdapter extends BaseQuickAdapter<SearchContatsBean, BaseViewHolder> {


    public SearchContactsAdapter(@Nullable List<SearchContatsBean> data) {
        super(R.layout.item_new_friend, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SearchContatsBean item) {
        ItemNewFriendBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvName.setText(item.getNick_name());
        ImageUtils.showAvatar(item.getImage(), binding.ivAvatar);
        binding.tvOption.setVisibility(View.GONE);
        binding.getRoot().setOnClickListener((v) -> {
            IntentUtils.intent2ContactsDetailActivity(item);
        });
    }
}

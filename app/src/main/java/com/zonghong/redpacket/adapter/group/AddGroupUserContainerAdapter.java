package com.zonghong.redpacket.adapter.group;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.ContactsAdapter;
import com.zonghong.redpacket.databinding.ItemContactsContainerBinding;

import java.util.List;

public class AddGroupUserContainerAdapter extends BaseQuickAdapter<ContatsChildBean, BaseViewHolder> {


    public AddGroupUserContainerAdapter(@Nullable List<ContatsChildBean> data) {
        super(R.layout.item_contacts_container, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ContatsChildBean item) {
        ItemContactsContainerBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvTitle.setText(item.getChart());
        binding.rvList.setLayoutManager(new LinearLayoutManager(MAPP.mapp, RecyclerView.VERTICAL, false));
        binding.rvList.setAdapter(new AddGroupUserAdapter(item.getList()));
    }
}

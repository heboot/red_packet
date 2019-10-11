package com.zonghong.redpacket.adapter.phonecontacts;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.ItemContactsContainerBinding;

import java.util.List;

public class PhoneContactsContainerAdapter extends BaseQuickAdapter<ContatsChildBean, BaseViewHolder> {

    private boolean choose;

    public PhoneContactsContainerAdapter(@Nullable List<ContatsChildBean> data, boolean choose) {
        super(R.layout.item_contacts_container, data);
        this.choose = choose;
    }


    @Override
    protected void convert(BaseViewHolder helper, ContatsChildBean item) {
        ItemContactsContainerBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvTitle.setText(item.getChart());
        binding.rvList.setLayoutManager(new LinearLayoutManager(MAPP.mapp, RecyclerView.VERTICAL, false));
        binding.rvList.setAdapter(new PhoneContactsAdapter(item.getList()));
    }
}

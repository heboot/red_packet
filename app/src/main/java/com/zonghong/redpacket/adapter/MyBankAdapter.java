package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.BankListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.ItemBankMyBinding;
import com.zonghong.redpacket.databinding.ItemContactsBinding;

import java.util.List;

public class MyBankAdapter extends BaseQuickAdapter<BankListBean, BaseViewHolder> {


    public MyBankAdapter(@Nullable List<BankListBean> data) {
        super(R.layout.item_bank_my, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, BankListBean item) {
        ItemBankMyBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvName.setText(item.getName());
        binding.tvOption.setOnClickListener((v) -> {
            // TODO: 2019-09-19 解绑
        });
    }
}

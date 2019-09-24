package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.CashLogBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.ItemCashBinding;
import com.zonghong.redpacket.databinding.ItemCommonTextArrowBinding;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.List;
import java.util.Map;

public class CashLogAdapter extends BaseQuickAdapter<CashLogBean, BaseViewHolder> {


    public CashLogAdapter(@Nullable List<CashLogBean> data) {
        super(R.layout.item_cash, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, CashLogBean item) {
        ItemCashBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.getRoot().setOnClickListener((v) -> {
        });
    }
}

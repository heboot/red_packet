package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.BalanceLogBean;
import com.waw.hr.mutils.bean.CashLogBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.ItemBalanceLogBinding;
import com.zonghong.redpacket.databinding.ItemCashBinding;
import com.zonghong.redpacket.utils.NumberUtils;

import java.util.List;

public class BalanceLogAdapter extends BaseQuickAdapter<BalanceLogBean, BaseViewHolder> {

    // type 0 红包  1 转账  2 充值  3  提现
    public BalanceLogAdapter(@Nullable List<BalanceLogBean> data) {
        super(R.layout.item_balance_log, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, BalanceLogBean item) {
        ItemBalanceLogBinding binding = DataBindingUtil.bind(helper.itemView);

        if (Double.parseDouble(item.getSign()) == 1) {
            binding.tvMoney.setTextColor(Color.parseColor("#F8D347"));
            binding.tvMoney.setText("+" + NumberUtils.formatDouble(Double.parseDouble(item.getMoney())));
        } else if (Double.parseDouble(item.getSign()) == 2) {
            binding.tvMoney.setTextColor(Color.BLACK);
            binding.tvMoney.setText("-" + NumberUtils.formatDouble(Double.parseDouble(item.getMoney())));
        }
        binding.tvTime.setText(item.getCreate_time());
        switch (item.getType()) {
            case 0:
                binding.tvName.setText("红包");
                break;
            case 1:
                binding.tvName.setText("转账");
                break;
            case 2:
                binding.tvName.setText("充值");
                break;
            case 3:
                binding.tvName.setText("提现");
                break;
            case 4:
                binding.tvName.setText("建群");
                break;
            case 10:
                binding.tvName.setText("红包退还");
                break;
        }
    }
}

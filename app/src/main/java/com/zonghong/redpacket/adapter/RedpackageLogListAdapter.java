package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.GetRedpackageUserBean;
import com.waw.hr.mutils.bean.RedpackageLogBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.ItemRedpackageLogBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.NumberUtils;

import java.util.List;

public class RedpackageLogListAdapter extends BaseQuickAdapter<RedpackageLogBean.ListBean, BaseViewHolder> {

    private boolean log;

    public RedpackageLogListAdapter(@Nullable List<RedpackageLogBean.ListBean> data) {
        super(R.layout.item_redpackage_log, data);
        this.log = log;
    }


    @Override
    protected void convert(BaseViewHolder helper, RedpackageLogBean.ListBean item) {
        ItemRedpackageLogBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.ivAvatar.setVisibility(View.GONE);
        binding.tvMoney.setText(NumberUtils.formatDouble(item.getMoney()) + "");
        binding.tvName.setText(item.getNick_name());
        binding.tvTime.setText(item.getCreate_time());
    }
}

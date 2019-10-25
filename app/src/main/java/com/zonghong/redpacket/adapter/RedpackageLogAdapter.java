package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.waw.hr.mutils.bean.GetRedpackageUserBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.ItemContactsContainerBinding;
import com.zonghong.redpacket.databinding.ItemRedpackageLogBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.NumberUtils;

import java.util.List;

public class RedpackageLogAdapter extends BaseQuickAdapter<GetRedpackageUserBean, BaseViewHolder> {

    private boolean log;

    public RedpackageLogAdapter(@Nullable List<GetRedpackageUserBean> data, boolean log) {
        super(R.layout.item_redpackage_log, data);
        this.log = log;
    }


    @Override
    protected void convert(BaseViewHolder helper, GetRedpackageUserBean item) {
        ItemRedpackageLogBinding binding = DataBindingUtil.bind(helper.itemView);
        if (log) {
            binding.ivAvatar.setVisibility(View.GONE);
        } else {
            binding.ivAvatar.setVisibility(View.VISIBLE);
            ImageUtils.showAvatar(item.getAvatar(), binding.ivAvatar);
        }
        binding.tvMoney.setText(NumberUtils.formatDouble(Double.parseDouble(item.getMoney())));
        binding.tvName.setText(item.getName());
        binding.tvTime.setText(item.getTime());
    }
}

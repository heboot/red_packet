package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.bean.BankListBean;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.user.MyBankActivity;
import com.zonghong.redpacket.databinding.ItemBankMyBinding;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;

public class PayChooseBankAdapter extends BaseQuickAdapter<BankListBean, BaseViewHolder> {


    public PayChooseBankAdapter(@Nullable List<BankListBean> data) {
        super(R.layout.item_bank_my, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, BankListBean item) {
        ItemBankMyBinding binding = DataBindingUtil.bind(helper.itemView);

        binding.tvOption.setVisibility(View.GONE);

        if (StringUtils.isEmpty(item.getOption())) {
            ImageUtils.showImage(R.mipmap.icon_bank, binding.ivImg);
            binding.tvName.setText(item.getBank_name());
        } else {
            binding.tvName.setText("余额" + UserService.getInstance().getBalance());
            ImageUtils.showImage(R.mipmap.icon_balance, binding.ivImg);
        }

        binding.getRoot().setOnClickListener((v) -> {
            EventBus.getDefault().postSticky(new UserEvent.CHOOSE_BANK_EVENT(item));
        });
    }
}

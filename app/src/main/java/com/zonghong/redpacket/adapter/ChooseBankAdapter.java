package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.BankListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.user.ChooseBankActivity;
import com.zonghong.redpacket.databinding.ItemBankBinding;

import java.lang.ref.WeakReference;
import java.util.List;


public class ChooseBankAdapter extends BaseQuickAdapter<BankListBean, BaseViewHolder> {

    private WeakReference<ChooseBankActivity> chooseBankActivityWeakReference;

    public ChooseBankAdapter(@Nullable List<BankListBean> data, WeakReference<ChooseBankActivity> chooseBankActivityWeakReference) {
        super(R.layout.item_bank, data);
        this.chooseBankActivityWeakReference = chooseBankActivityWeakReference;
    }


    @Override
    protected void convert(BaseViewHolder helper, BankListBean item) {
        ItemBankBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvName.setText(item.getName());
        binding.getRoot().setOnClickListener((v) -> {
//            EventBus.getDefault().post(new UserEvent.CHOOSE_BANK_EVENT(item));
//            EventBus.getDefault().post(new UserEvent.LOGIN_SUC_EVENT());
            chooseBankActivityWeakReference.get().chooseBank(item);
        });
    }
}

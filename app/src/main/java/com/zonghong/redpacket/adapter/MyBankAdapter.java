package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.BankListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.user.MyBankActivity;
import com.zonghong.redpacket.databinding.ItemBankMyBinding;
import com.zonghong.redpacket.databinding.ItemContactsBinding;

import java.lang.ref.WeakReference;
import java.util.List;

public class MyBankAdapter extends BaseQuickAdapter<BankListBean, BaseViewHolder> {


    private WeakReference<MyBankActivity> weakReference;

    public MyBankAdapter(@Nullable List<BankListBean> data, WeakReference<MyBankActivity> weakReference) {
        super(R.layout.item_bank_my, data);
        this.weakReference = weakReference;
    }


    @Override
    protected void convert(BaseViewHolder helper, BankListBean item) {
        ItemBankMyBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvName.setText(item.getBank_name());
        binding.tvOption.setOnClickListener((v) -> {
            weakReference.get().showConfirmDialog(item);
        });
    }
}

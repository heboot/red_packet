package com.zonghong.redpacket.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zonghong.redpacket.R;

import java.util.List;

public class ChooseBankAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ChooseBankAdapter(@Nullable List<String> data) {
        super(R.layout.item_bank, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}

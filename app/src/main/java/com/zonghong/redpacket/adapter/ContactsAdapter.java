package com.zonghong.redpacket.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zonghong.redpacket.R;

import java.util.List;

public class ContactsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ContactsAdapter(@Nullable List<String> data) {
        super(R.layout.item_contacts, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}

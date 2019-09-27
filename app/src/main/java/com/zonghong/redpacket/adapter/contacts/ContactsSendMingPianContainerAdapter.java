package com.zonghong.redpacket.adapter.contacts;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ChooseContactsSendMingPianActivity;
import com.zonghong.redpacket.adapter.ContactsAdapter;
import com.zonghong.redpacket.databinding.ItemContactsContainerBinding;

import java.lang.ref.WeakReference;
import java.util.List;

public class ContactsSendMingPianContainerAdapter extends BaseQuickAdapter<ContatsChildBean, BaseViewHolder> {


    private WeakReference<ChooseContactsSendMingPianActivity> weakReference;

    public ContactsSendMingPianContainerAdapter(@Nullable List<ContatsChildBean> data, WeakReference<ChooseContactsSendMingPianActivity> weakReference) {
        super(R.layout.item_contacts_container, data);
        this.weakReference = weakReference;
    }


    @Override
    protected void convert(BaseViewHolder helper, ContatsChildBean item) {
        ItemContactsContainerBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvTitle.setText(item.getChart());
        binding.rvList.setLayoutManager(new LinearLayoutManager(MAPP.mapp, RecyclerView.VERTICAL, false));
        binding.rvList.setAdapter(new SendMingpianContactsAdapter(item.getList(), weakReference));
    }
}

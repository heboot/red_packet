package com.zonghong.redpacket.adapter.group;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.ContatsFriendBean;
import com.waw.hr.mutils.bean.IntiveUserBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.chat.GroupManagerActivity;
import com.zonghong.redpacket.databinding.ItemContactsBinding;
import com.zonghong.redpacket.databinding.ItemContactsInviteBinding;
import com.zonghong.redpacket.utils.ImageUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class InviteGroupUserAdapter extends BaseQuickAdapter<IntiveUserBean.ListBean, BaseViewHolder> {


    private WeakReference<GroupManagerActivity> weakReference;


    public InviteGroupUserAdapter(@Nullable List<IntiveUserBean.ListBean> data, WeakReference<GroupManagerActivity> weakReference) {
        super(R.layout.item_contacts_invite, data);
        this.weakReference = weakReference;
    }


    @Override
    protected void convert(BaseViewHolder helper, IntiveUserBean.ListBean item) {
        ItemContactsInviteBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.cb.setVisibility(View.GONE);
        binding.tvName.setText(item.getNick_name());
        ImageUtils.showAvatar(item.getImage(), binding.ivAvatar);
        binding.tvSubtitle.setText(item.getInvite_name());

        if(item.getWart_consent() == 1){
            binding.tvPass.setVisibility(View.VISIBLE);
            binding.tvOption.setVisibility(View.VISIBLE);
        }else{
            binding.tvPass.setVisibility(View.GONE);
            binding.tvOption.setVisibility(View.GONE);
        }

        binding.tvOption.setOnClickListener(view->{
            weakReference.get().executeOperation(item.getUser_id()+"",1);
        });
        binding.tvPass.setOnClickListener(view->{
            weakReference.get().executeOperation(item.getUser_id()+"",0);
        });
    }
}

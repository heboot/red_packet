package com.zonghong.redpacket.adapter.group;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.GroupUserListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.ItemContactsBinding;
import com.zonghong.redpacket.utils.ImageUtils;

import java.util.List;

public class SetGroupForbidRedpackageAdapter extends BaseQuickAdapter<GroupUserListBean.GUserBean, BaseViewHolder> {

    private boolean isChoose = false;

    public SetGroupForbidRedpackageAdapter(@Nullable List<GroupUserListBean.GUserBean> data) {
        super(R.layout.item_contacts, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, GroupUserListBean.GUserBean item) {
        ItemContactsBinding binding = DataBindingUtil.bind(helper.itemView);
        if (item.getDisabled() >= 1) {
            binding.cb.setChecked(true);
        }else{
            binding.cb.setChecked(false);
        }
        binding.cb.setVisibility(View.VISIBLE);
        binding.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    item.setCheck(true);
                } else {
                    item.setCheck(false);
                }
            }
        });



        binding.tvName.setText(item.getNick_name());
        ImageUtils.showAvatar(item.getImage(), binding.ivAvatar);

//        if (!isChoose) {
//            binding.getRoot().setOnClickListener((v) -> {
////                RongUtils.toChat(String.valueOf(item.getFriend_id()), item.getReal_name());
//                SearchContatsBean searchContatsBean = new SearchContatsBean();
//                searchContatsBean.setID(String.valueOf(item.getUser_id()));
//                IntentUtils.intent2ContactsDetailActivity(searchContatsBean);
//            });
//        }


    }
}

package com.zonghong.redpacket.adapter.contacts;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.ContatsFriendBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ChooseContactsSendMingPianActivity;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.ItemContactsBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class SendMingpianContactsAdapter extends BaseQuickAdapter<ContatsFriendBean, BaseViewHolder> {


    private WeakReference<ChooseContactsSendMingPianActivity> weakReference;


    public SendMingpianContactsAdapter(@Nullable List<ContatsFriendBean> data, WeakReference<ChooseContactsSendMingPianActivity> weakReference) {
        super(R.layout.item_contacts, data);
        this.weakReference = weakReference;
    }


    @Override
    protected void convert(BaseViewHolder helper, ContatsFriendBean item) {
        ItemContactsBinding binding = DataBindingUtil.bind(helper.itemView);

        binding.cb.setVisibility(View.VISIBLE);

        binding.cb.setOnClickListener(view -> {
            if (item.isCheck()) {
//                binding.cb.setChecked(false);
                weakReference.get().setCheckIds("");
                notifyDataSetChanged();

            } else {
                weakReference.get().setCheckIds(item.getFriend_id() + "");
//                binding.cb.setChecked(true);
                notifyDataSetChanged();
            }
        });

        if (String.valueOf(item.getFriend_id()).equals(weakReference.get().getCheckIds())) {
            binding.cb.setChecked(true);
            item.setCheck(true);
        } else {
            item.setCheck(false);
            binding.cb.setChecked(false);
        }

        binding.tvName.setText(item.getNick_name());
        ImageUtils.showAvatar(item.getImage(), binding.ivAvatar);


    }
}

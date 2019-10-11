package com.zonghong.redpacket.adapter.phonecontacts;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsFriendBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.ItemContactsPhoneBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PhoneContactsAdapter extends BaseQuickAdapter<ContatsFriendBean, BaseViewHolder> {

    private boolean isChoose = false;

    public PhoneContactsAdapter(@Nullable List<ContatsFriendBean> data) {
        super(R.layout.item_contacts_phone, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ContatsFriendBean item) {
        ItemContactsPhoneBinding binding = DataBindingUtil.bind(helper.itemView);
        if (isChoose) {
            binding.cb.setVisibility(View.VISIBLE);
        } else {
            binding.cb.setVisibility(View.GONE);
        }

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
        binding.tvJyname.setText("简易: " + item.getUsername());
        binding.tvOption.setOnClickListener(view -> {
            // TODO: 2019/10/11 0011  add friend
            delFriend(item.getUser_id() + "");
        });

        if(item.getIs_friend() == 1){
            binding.tvOption.setVisibility(View.GONE);
            binding.tvAdded.setVisibility(View.VISIBLE);
        }else{
            binding.tvOption.setVisibility(View.VISIBLE);
            binding.tvAdded.setVisibility(View.GONE);
        }



    }

    private QMUIDialog qmuiDialog;


    private void delFriend(String userId) {
        Map params = new HashMap<>();
        params = new HashMap<>();
        params.put("friend_id", userId);
        params.put("notes", "");
        HttpClient.Builder.getServer().fSendMsg(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Boolean>() {
            @Override
            public void onSuccess(BaseBean<Boolean> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }

            @Override
            public void onError(BaseBean<Boolean> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }

}

package com.zonghong.redpacket.adapter;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsFriendBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ContactsDetailActivity;
import com.zonghong.redpacket.activity.user.AlterPayPwdActivity;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.ItemContactsBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.view.DelFriendDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactsAdapter extends BaseQuickAdapter<ContatsFriendBean, BaseViewHolder> {

    private boolean isChoose = false;

    public ContactsAdapter(@Nullable List<ContatsFriendBean> data) {
        super(R.layout.item_contacts, data);
    }

    public ContactsAdapter(@Nullable List<ContatsFriendBean> data, boolean isChoose) {
        super(R.layout.item_contacts, data);
        this.isChoose = isChoose;
    }


    @Override
    protected void convert(BaseViewHolder helper, ContatsFriendBean item) {
        ItemContactsBinding binding = DataBindingUtil.bind(helper.itemView);
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

        if (!isChoose) {
            binding.getRoot().setOnClickListener((v) -> {
//                RongUtils.toChat(String.valueOf(item.getFriend_id()), item.getReal_name());
                IntentUtils.intent2ContactsDetailActivity(item.getFriend_id() + "", ContactsDetailType.NORMAL);
            });

            binding.getRoot().setOnLongClickListener(v -> {
                showDelDialog(item.getNick_name(),item.getFriend_id()+"");
                return false;
            });

        }


    }

    private DelFriendDialog qmuiDialog;

    private void showDelDialog(String name,String id) {
        qmuiDialog = DelFriendDialog.newInstance(id);
        qmuiDialog.show(((FragmentActivity)MAPP.mapp.getCurrentActivity()).getSupportFragmentManager(),"");
    }

    private void delFriend(String userId) {
        Map params = new HashMap<>();
        params.put("friend_id", userId);
        HttpClient.Builder.getServer().fDel(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }

}

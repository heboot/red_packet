package com.zonghong.redpacket.activity.contacts;

import android.view.View;
import android.widget.CompoundButton;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.waw.hr.mutils.bean.UserInfoBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.ActivityContactsDetailBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactsDetailActivity extends BaseActivity<ActivityContactsDetailBinding> {

//    private SearchContatsBean searchContatsBean;

    private UserInfoBean userInfoBean;

    private ContactsDetailType contactsDetailType = ContactsDetailType.NORMAL;

    private String userId;

    private String groupId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contacts_detail;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("");
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
    }

    @Override
    public void initData() {
//        searchContatsBean = (SearchContatsBean) getIntent().getExtras().get(MKey.DATA);
        userId = getIntent().getStringExtra(MKey.USER_ID);
        contactsDetailType = (ContactsDetailType) getIntent().getExtras().get(MKey.TYPE);
        if (contactsDetailType == ContactsDetailType.GROUP) {
            groupId = getIntent().getStringExtra(MKey.ID);
            binding.tvClose.setVisibility(View.VISIBLE);
            binding.sbBanned.setVisibility(View.VISIBLE);
        }
        userInfo();
    }


    private void userInfo() {
        loadingDialog.show();
        params = new HashMap<>();
        params.put("id", userId);
        params.put("addFriend", "");
        HttpClient.Builder.getServer().userInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<UserInfoBean>() {
            @Override
            public void onSuccess(BaseBean<UserInfoBean> baseBean) {
                dismissLoadingDialog();
                userInfoBean = baseBean.getData();
                binding.tvName.setText(userInfoBean.getNick_name());
                ImageUtils.showAvatar(userInfoBean.getImg(), binding.ivAvatar);
                binding.tvNo.setText(userInfoBean.getAccount_id());
//                查看用户的信息对当前用户的关系; 0处于非好友; 1是处于待同意; 2是处于好友; 4是处于黑名单
                if (userInfoBean.getStatus() == 0) {
                    binding.tvAdd.setText("添加好友");
                    binding.tvAdd.setEnabled(true);
                } else if (userInfoBean.getStatus() == 1) {
                    binding.tvAdd.setVisibility(View.GONE);
                } else if (userInfoBean.getStatus() == 2) {
                    binding.tvDel.setVisibility(View.VISIBLE);
                } else if (userInfoBean.getStatus() == 3) {
                    binding.tvAdd.setVisibility(View.GONE);
                    binding.sbAddBlack.setCheckedNoEvent(true);
                }
            }

            @Override
            public void onError(BaseBean<UserInfoBean> baseBean) {
                dismissLoadingDialog();
                tipDialog = DialogUtils.getFailDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    @Override
    public void initListener() {
        binding.tvAdd.setOnClickListener((v) -> {
            add();
        });
        binding.sbAddBlack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    addBlack();
                } else {
                    removeBlack();
                }
            }
        });
    }

    private void addBlack() {
        loadingDialog.show();
        params = new HashMap<>();
        params.put("friend_id", userId);
        HttpClient.Builder.getServer().fAddBlack(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getSuclDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void removeBlack() {
        loadingDialog.show();
        params = new HashMap<>();
        params.put("friend_id", userId);
        HttpClient.Builder.getServer().fReBla(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getSuclDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void add() {
        loadingDialog.show();
        params = new HashMap<>();
        params.put("friend_id", userId);
        params.put("notes", "");
        HttpClient.Builder.getServer().fSendMsg(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Boolean>() {
            @Override
            public void onSuccess(BaseBean<Boolean> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getSuclDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Boolean> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}

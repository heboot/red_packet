package com.zonghong.redpacket.activity.common;

import android.view.View;
import android.widget.CompoundButton;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.HelpBean;
import com.waw.hr.mutils.bean.SafePrivacyBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.common.HelpAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivitySafeBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SafeActivity extends BaseActivity<ActivitySafeBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_safe;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("安全与隐私");
    }

    @Override
    public void initData() {
        getStatus();
    }

    @Override
    public void initListener() {
        binding.sbAddEnable.setOnCheckedChangeListener((buttonView, isChecked) -> setIncrease());
        binding.sbJyEnable.setOnCheckedChangeListener((buttonView, isChecked) -> searchAccount());
        binding.sbPhoneEnable.setOnCheckedChangeListener((buttonView, isChecked) -> searchPhone());
        binding.sbVreifyEnable.setOnCheckedChangeListener((buttonView, isChecked) -> setFriendCheck());
    }

    public void getStatus() {
        params = new HashMap<>();
        HttpClient.Builder.getServer().getSafePrivacy(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<SafePrivacyBean>() {
            @Override
            public void onSuccess(BaseBean<SafePrivacyBean> baseBean) {
                if(baseBean.getData().getConsent() == 0){
                    binding.sbAddEnable.setCheckedNoEvent(true);
                }else{
                    binding.sbAddEnable.setCheckedNoEvent(false);
                }

                if(baseBean.getData().getSearch_phone() == 0){
                    binding.sbPhoneEnable.setCheckedNoEvent(true);
                }else{
                    binding.sbPhoneEnable.setCheckedNoEvent(false);
                }

                if(baseBean.getData().getSearch_ID() == 0){
                    binding.sbJyEnable.setCheckedNoEvent(true);
                }else{
                    binding.sbJyEnable.setCheckedNoEvent(false);
                }

                if(baseBean.getData().getFriend_check() == 0){
                    binding.sbVreifyEnable.setCheckedNoEvent(true);
                }else{
                    binding.sbVreifyEnable.setCheckedNoEvent(false);
                }
            }

            @Override
            public void onError(BaseBean<SafePrivacyBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(SafeActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void setIncrease() {
        params = new HashMap<>();
        params.put("execOption", binding.sbAddEnable.isChecked() ? 0 : 1);
        HttpClient.Builder.getServer().setIncrease(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(SafeActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void setFriendCheck() {
        params = new HashMap<>();
        params.put("execOption", binding.sbVreifyEnable.isChecked() ? 0 : 1);
        HttpClient.Builder.getServer().setFriendCheck(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(SafeActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void searchPhone() {
        params = new HashMap<>();
        params.put("execOption", binding.sbAddEnable.isChecked() ? 0 : 1);
        HttpClient.Builder.getServer().searchPhone(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(SafeActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void searchAccount() {
        params = new HashMap<>();
        params.put("execOption", binding.sbAddEnable.isChecked() ? 0 : 1);
        HttpClient.Builder.getServer().searchAccount(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(SafeActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}

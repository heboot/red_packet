package com.zonghong.redpacket.activity.loginregister;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.LoginBean;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityLoginBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.eventbus.EventBus;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initUI() {
        binding.qlytPhone.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytPwd.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
    }

    @Override
    public void initData() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
        EventBus.getDefault().register(this);
        if (!StringUtils.isEmpty(UserService.getInstance().getToken()) && !StringUtils.isEmpty(UserService.getInstance().getRy_token())) {
            RongUtils.connect(UserService.getInstance().getRy_token());
        }
    }

    @Override
    public void initListener() {
        binding.llytRegister.setOnClickListener((v) -> {
            IntentUtils.doIntent(RegisterActivity.class);
        });
        binding.tvForget.setOnClickListener((v) -> {
            IntentUtils.doIntent(ForgetActivity.class);
        });
        binding.tvLogin.setOnClickListener((v) -> {
            login();
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserEvent.LOGIN_SUC_EVENT event) {
        finish();
    }

    private void login() {
        if (StringUtils.isEmpty(binding.etPhone.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入手机号码", true);
            tipDialog.show();
            return;
        }

        if (StringUtils.isEmpty(binding.etPwd.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入密码", true);
            tipDialog.show();
            return;
        }

        loadingDialog.show();

        params.put(MKey.PHONE, binding.etPhone.getText());
        params.put(MKey.PASSWORD, binding.etPwd.getText());

        HttpClient.Builder.getServer().uLogin(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<LoginBean>() {
            @Override
            public void onSuccess(BaseBean<LoginBean> baseBean) {
                loadingDialog.dismiss();
                UserService.getInstance().setToken(baseBean.getData().getToken());
                UserService.getInstance().setRy_token(baseBean.getData().getRy_token());
                RongUtils.connect(baseBean.getData().getRy_token());
            }

            @Override
            public void onError(BaseBean<LoginBean> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(LoginActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}

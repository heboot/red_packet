package com.zonghong.redpacket.activity.user;

import android.content.DialogInterface;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.loginregister.RegisterActivity;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.CheckCodeType;
import com.zonghong.redpacket.databinding.ActivityAlterPwdBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AlterPwdActivity extends BaseActivity<ActivityAlterPwdBinding> {


    private CheckCodeType checkCodeType;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_pwd;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("修改密码");
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
        checkCodeType = (CheckCodeType) getIntent().getExtras().get(MKey.TYPE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.tvSubmit.setOnClickListener((v) -> {
            if (checkCodeType == CheckCodeType.PAY_PASSWORD) {
                alterPayPwd();
            } else if (checkCodeType == CheckCodeType.PASSWORD) {
                alterPwd();
            }
        });
    }

    private void alterPwd() {
        if (StringUtils.isEmpty(binding.etPassword.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入手机号码", true);
            tipDialog.show();
            return;
        }
//        if (StringUtils.isEmpty(binding.etCode.getText())) {
//            tipDialog = DialogUtils.getFailDialog(this, "请输入验证码", true);
//            tipDialog.show();
//            return;
//        }
//        if (StringUtils.isEmpty(binding.etPwd.getText())) {
//            tipDialog = DialogUtils.getFailDialog(this, "请输入密码", true);
//            tipDialog.show();
//            return;
//        }
//        if (!StringUtils.isEmpty(code) && !code.equals(binding.etCode.getText().toString().trim())) {
//            tipDialog = DialogUtils.getFailDialog(this, "验证码不正确", true);
//            tipDialog.show();
//            return;
//        }

        loadingDialog.show();
        params = new HashMap<>();
        params.put(MKey.PHONE, binding.etPassword.getText());
//        params.put(MKey.CODE, StringUtils.isEmpty(binding.etCode.getText()) ? "" : binding.etCode.getText());
//        params.put(MKey.PASSWORD, binding.etPwd.getText());
        HttpClient.Builder.getServer().uCreate(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                dismissLoadingDialog();
//                UserService.getInstance().setToken(baseBean.getData());
                tipDialog = DialogUtils.getSuclDialog(AlterPwdActivity.this, baseBean.getMsg(), true);
                tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                    }
                });
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                dismissLoadingDialog();
                tipDialog = DialogUtils.getFailDialog(AlterPwdActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private void alterPayPwd() {
        loadingDialog.show();
        params = new HashMap<>();
        params.put("pay_pass", binding.etPassword.getText().toString());
        params.put("re_pass", binding.etPassword.getText().toString());
        HttpClient.Builder.getServer().upPay(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getSuclDialog(AlterPwdActivity.this, baseBean.getMsg(), true);
                tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                    }
                });
                tipDialog.show();

            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(AlterPwdActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}

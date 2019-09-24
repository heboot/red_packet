package com.zonghong.redpacket.activity.user;

import android.content.DialogInterface;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.CheckCodeType;
import com.zonghong.redpacket.databinding.ActivityAlterPayPwdBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AlterPayPwdActivity extends BaseActivity<ActivityAlterPayPwdBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_pay_pwd;
    }

    @Override
    public void initUI() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
        setBackVisibility(View.VISIBLE);

        if (UserService.getInstance().getUserInfoBean().getSign() == 0) {
            binding.includeToolbar.tvTitle.setText("设置支付密码");
            binding.etPwd.setVisibility(View.GONE);
            binding.tvPhoneTitle.setVisibility(View.GONE);
        } else {
            binding.includeToolbar.tvTitle.setText("修改支付密码");
        }

        binding.includeToolbar.tvRight.setVisibility(View.VISIBLE);
        binding.includeToolbar.tvRight.setText("完成");
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        binding.tvForget.setOnClickListener((v) -> {
            IntentUtils.intent2VerifyCodeActivity(CheckCodeType.PAY_PASSWORD);
        });
        binding.includeToolbar.tvRight.setOnClickListener((v) -> {


            if (UserService.getInstance().getUserInfoBean().getSign() == 1) {
                if (StringUtils.isEmpty(binding.etPwd.getText())) {
                    tipDialog = DialogUtils.getFailDialog(this, "请输入旧密码", true);
                    tipDialog.show();
                    return;
                }
            }


            if (StringUtils.isEmpty(binding.etNewPwd.getText())) {
                tipDialog = DialogUtils.getFailDialog(this, "请输入新密码", true);
                tipDialog.show();
                return;
            }

            if (StringUtils.isEmpty(binding.etNewConfimpwd.getText())) {
                tipDialog = DialogUtils.getFailDialog(this, "请输入确认新密码", true);
                tipDialog.show();
                return;
            }

            if (!binding.etNewPwd.getText().toString().equals(binding.etNewConfimpwd.getText().toString())) {
                tipDialog = DialogUtils.getFailDialog(this, "两次密码输入不一致", true);
                tipDialog.show();
                return;
            }

            upPay();
        });

    }

    private void upPay() {
        loadingDialog.show();
        params = new HashMap<>();
        params.put("pay_pass", binding.etNewPwd.getText().toString());
        if (UserService.getInstance().getUserInfoBean().getSign() == 1) {
            params.put("old_payPass", binding.etPwd.getText().toString());
        }
        params.put("repass", binding.etNewConfimpwd.getText().toString());
        HttpClient.Builder.getServer().upPay(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                UserService.getInstance().getUserInfoBean().setSign(1);
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getSuclDialog(AlterPayPwdActivity.this, baseBean.getMsg(), true);
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
                tipDialog = DialogUtils.getFailDialog(AlterPayPwdActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


}

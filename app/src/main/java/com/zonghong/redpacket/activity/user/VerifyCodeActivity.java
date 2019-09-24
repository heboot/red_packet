package com.zonghong.redpacket.activity.user;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.ObserableUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.loginregister.RegisterActivity;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.CheckCodeType;
import com.zonghong.redpacket.databinding.ActivityVerifyCodeBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VerifyCodeActivity extends BaseActivity<ActivityVerifyCodeBinding> {

    private CheckCodeType checkCodeType;

    private String code;

    private Observer countDownObserver;

    private Observable observable;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify_code;
    }

    @Override
    public void initUI() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
    }

    @Override
    public void initData() {
        binding.tvTitle.setText("您的密保手机号码是" + UserService.getInstance().getPhone().substring(0, 3) + "******** 请点击“获取验证码”：");


        checkCodeType = (CheckCodeType) getIntent().getExtras().get(MKey.TYPE);
        countDownObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                if (integer <= 0) {
                    binding.tvSendcode.setText("获取验证码");
                } else {
                    binding.tvSendcode.setText(integer + "");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void initListener() {
        binding.tvSendcode.setOnClickListener((v) -> {
            sendCode();
        });
        binding.tvSubmit.setOnClickListener((v) -> {
            submit();
        });
    }


    private void submit() {

        if (StringUtils.isEmpty(binding.etPhone.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入验证码", true);
            tipDialog.show();
            return;
        }

        if (!StringUtils.isEmpty(code) && !code.equals(binding.etPhone.getText().toString().trim())) {
            tipDialog = DialogUtils.getFailDialog(this, "验证码不正确", true);
            tipDialog.show();
            return;
        }

        IntentUtils.intent2AlterPwdActivity(checkCodeType);


    }

    private void sendCode() {

        binding.tvSendcode.setEnabled(false);
        params.put(MKey.PHONE, UserService.getInstance().getPhone());

        HttpClient.Builder.getServer().sendVerify(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<String>() {
            @Override
            public void onSuccess(BaseBean<String> baseBean) {

                code = baseBean.getData();

                observable = ObserableUtils.countdown(60);

                observable.subscribe(countDownObserver);
            }

            @Override
            public void onError(BaseBean<String> baseBean) {
                binding.tvSendcode.setEnabled(true);
                tipDialog = DialogUtils.getFailDialog(VerifyCodeActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


}

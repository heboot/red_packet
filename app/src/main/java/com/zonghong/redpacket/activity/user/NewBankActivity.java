package com.zonghong.redpacket.activity.user;

import android.content.DialogInterface;
import android.view.View;

import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityNewBankBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewBankActivity extends BaseActivity<ActivityNewBankBinding> {

    private QMUIDialog qmuiDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_bank;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("添加银行卡");
    }

    @Override
    public void initData() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
    }

    @Override
    public void initListener() {
        binding.tvSubmit.setOnClickListener((v) -> {

            if (UserService.getInstance().getUserInfoBean().getSign() == 0) {
                if (qmuiDialog == null) {
                    qmuiDialog = new QMUIDialog.MessageDialogBuilder(this)
                            .setMessage("您需要设置支付密码后才能添加银行卡")
                            .addAction("取消", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    qmuiDialog.dismiss();
                                }
                            })
                            .addAction("确定", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    qmuiDialog.dismiss();
                                    // TODO: 2019-09-24
                                    IntentUtils.doIntent(AlterPayPwdActivity.class);
                                }
                            })
                            .create();
                }
                qmuiDialog.show();
                return;
            }

            addBank();
        });
    }

    private void addBank() {

        if (StringUtils.isEmpty(binding.etBankName.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入开户行名称", true);
            tipDialog.show();
            return;
        }

        if (StringUtils.isEmpty(binding.etName.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入持卡人姓名", true);
            tipDialog.show();
            return;
        }

        if (StringUtils.isEmpty(binding.etNumber.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入银行卡号", true);
            tipDialog.show();
            return;
        }


        loadingDialog.show();
        params.put("name", binding.etName.getText().toString());
        params.put("bank_name", binding.etBankName.getText().toString());
        params.put("card", binding.etNumber.getText().toString());
        HttpClient.Builder.getServer().add_bank(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getSuclDialog(NewBankActivity.this, baseBean.getMsg(), true);
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
                tipDialog = DialogUtils.getFailDialog(NewBankActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}

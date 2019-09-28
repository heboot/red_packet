package com.zonghong.redpacket.activity.user;

import android.view.View;

import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.BankListBean;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.UserInfoBean;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ContactsDetailActivity;
import com.zonghong.redpacket.activity.redpackage.NewRedPackageActivity;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.PayDialogType;
import com.zonghong.redpacket.common.RechargeCashType;
import com.zonghong.redpacket.common.RedPackageType;
import com.zonghong.redpacket.databinding.ActivityRechargeBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.utils.RechargeMoneyTextWatcher;
import com.zonghong.redpacket.view.PayDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TransferAccountActivity extends BaseActivity<ActivityRechargeBinding> {

    private RechargeCashType type;

    private PayDialog payDialog;

//    private BankListBean currentBank;

    private QMUIDialog qmuiDialog;

    private String toId;

    private UserInfoBean userInfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }


    @Override
    public void initUI() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
        EventBus.getDefault().register(this);
        binding.qlytContainer.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x10)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        type = (RechargeCashType) getIntent().getExtras().get(MKey.TYPE);
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("转账"); //根据类型判断
        binding.tvType.setText("转账金额");

    }

    @Override
    public void initData() {
        toId = getIntent().getStringExtra(MKey.USER_ID);
        userInfo();
//        bankList();
        binding.tvCard.setText(UserService.getInstance().getBalance());
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (qmuiDialog != null) {
//            bankList();
            userInfo();
        }
    }

    @Override
    public void initListener() {
        binding.etMoney.addTextChangedListener(new RechargeMoneyTextWatcher(binding.etMoney));
        binding.tvSubmit.setOnClickListener((v) -> {


            if (StringUtils.isEmpty(binding.etMoney.getText())) {
                tipDialog = DialogUtils.getFailDialog(this, "请输入金额", true);
                tipDialog.show();
                return;
            }

            if (Double.parseDouble(binding.etMoney.getText().toString()) <= 0) {
                tipDialog = DialogUtils.getFailDialog(this, "请输入金额", true);
                tipDialog.show();
                return;
            }


            payDialog = PayDialog.newInstance(PayDialogType.ZHUANZHUANG, Float.parseFloat((String) binding.etMoney.getText().toString()), toId, "0", userInfoBean.getNick_name());
            payDialog.show(getSupportFragmentManager(), "");
        });
        binding.includeToolbar.tvRight.setOnClickListener((v) -> {
            IntentUtils.doIntent(CashListActivity.class);
        });
    }


//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void onEvent(UserEvent.CHOOSE_BANK_EVENT event) {
////        currentBank = event.getBankListBean();
//        binding.tvCard.setText(event.getBankListBean().getBank_name());
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserEvent.ZHUANZHUANG_SUC_EVENT event) {
        finish();
    }

    private void userInfo() {
        params = new HashMap<>();
        params.put("id", toId);
        HttpClient.Builder.getServer().userInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<UserInfoBean>() {
            @Override
            public void onSuccess(BaseBean<UserInfoBean> baseBean) {
                userInfoBean = baseBean.getData();
            }

            @Override
            public void onError(BaseBean<UserInfoBean> baseBean) {
                dismissLoadingDialog();
                tipDialog = DialogUtils.getFailDialog(TransferAccountActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void bankList() {
        loadingDialog.show();
        Map params = new HashMap<>();
        HttpClient.Builder.getServer().bankList(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<BankListBean>>() {
            @Override
            public void onSuccess(BaseBean<List<BankListBean>> baseBean) {
                loadingDialog.dismiss();
                if (baseBean.getData() != null && baseBean.getData().size() > 0) {
                    binding.tvCard.setText(baseBean.getData().get(0).getBank_name());
//                    currentBank = baseBean.getData().get(0);
                } else {
                    if (qmuiDialog == null) {
                        qmuiDialog = new QMUIDialog.MessageDialogBuilder(TransferAccountActivity.this)
                                .setMessage("您需要绑定银行卡后才能转账")
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
                }
            }

            @Override
            public void onError(BaseBean<List<BankListBean>> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(TransferAccountActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


}

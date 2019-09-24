package com.zonghong.redpacket.activity.user;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;

import com.example.http.HttpClient;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.BankListBean;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.PayDialogType;
import com.zonghong.redpacket.common.RechargeCashType;
import com.zonghong.redpacket.databinding.ActivityRechargeBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.utils.RechargeMoneyTextWatcher;
import com.zonghong.redpacket.view.PayDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RechargeCashActivity extends BaseActivity<ActivityRechargeBinding> {

    private RechargeCashType type;

    private PayDialog payDialog;

    private BankListBean currentBank;

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
        if (type == RechargeCashType.RECHARGE) {
            binding.includeToolbar.tvTitle.setText("余额充值"); //根据类型判断
            binding.tvType.setText("充值金额");
        } else {
            binding.includeToolbar.tvRight.setVisibility(View.VISIBLE);
            binding.includeToolbar.tvRight.setBackground(null);
//            binding.includeToolbar.tvRight.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            binding.includeToolbar.tvRight.setTextColor(Color.BLACK);
            binding.includeToolbar.tvRight.setText("提现记录");
            binding.includeToolbar.tvTitle.setText("提现"); //根据类型判断
            binding.tvType.setText("提现金额");
        }

    }

    @Override
    public void initData() {
        bankList();
    }

    @Override
    public void initListener() {
        binding.etMoney.addTextChangedListener(new RechargeMoneyTextWatcher(binding.etMoney));
        binding.tvCard.setOnClickListener((v) -> {
            IntentUtils.intent2ChooseBankActivity(type);
        });
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


            payDialog = PayDialog.newInstance(type == RechargeCashType.RECHARGE ? PayDialogType.RECHARGE : PayDialogType.CASH, Float.parseFloat((String) binding.etMoney.getText().toString()), String.valueOf(currentBank.getID()));
            payDialog.show(getSupportFragmentManager(), "");
        });
        binding.includeToolbar.tvRight.setOnClickListener((v) -> {
            IntentUtils.doIntent(CashListActivity.class);
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(UserEvent.CHOOSE_BANK_EVENT event) {
        currentBank = event.getBankListBean();
        binding.tvCard.setText(event.getBankListBean().getBank_name());
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(UserEvent.CASH_SUC_EVENT event) {
        finish();
    }


    private void bankList() {
        loadingDialog.show();
        HttpClient.Builder.getServer().bankList(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<BankListBean>>() {
            @Override
            public void onSuccess(BaseBean<List<BankListBean>> baseBean) {
                loadingDialog.dismiss();
                if (baseBean.getData() != null && baseBean.getData().size() > 0) {
                    binding.tvCard.setText(baseBean.getData().get(0).getBank_name());
                    currentBank = baseBean.getData().get(0);
                }
            }

            @Override
            public void onError(BaseBean<List<BankListBean>> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(RechargeCashActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


}

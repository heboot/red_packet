package com.zonghong.redpacket.activity.user;

import android.view.View;

import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.PayDialogType;
import com.zonghong.redpacket.common.RechargeCashType;
import com.zonghong.redpacket.databinding.ActivityRechargeBinding;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.utils.RechargeMoneyTextWatcher;
import com.zonghong.redpacket.view.PayDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RechargeCashActivity extends BaseActivity<ActivityRechargeBinding> {

    private RechargeCashType type;

    private PayDialog payDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }


    @Override
    public void initUI() {
        EventBus.getDefault().register(this);
        binding.qlytContainer.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x10)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        type = (RechargeCashType) getIntent().getExtras().get(MKey.TYPE);
        setBackVisibility(View.VISIBLE);
        if (type == RechargeCashType.RECHARGE) {
            binding.includeToolbar.tvTitle.setText("余额充值"); //根据类型判断
        } else {
            binding.includeToolbar.tvTitle.setText("提现"); //根据类型判断
        }

    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        binding.etMoney.addTextChangedListener(new RechargeMoneyTextWatcher(binding.etMoney));
        binding.tvCard.setOnClickListener((v) -> {
            IntentUtils.intent2ChooseBankActivity(type);
        });
        binding.tvSubmit.setOnClickListener((v) -> {
            payDialog = PayDialog.newInstance(type == RechargeCashType.RECHARGE ? PayDialogType.RECHARGE : PayDialogType.CASH);
            payDialog.show(getSupportFragmentManager(), "");
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(UserEvent.CHOOSE_BANK_EVENT event) {
        binding.tvCard.setText(event.getBankListBean().getName());
    }


}

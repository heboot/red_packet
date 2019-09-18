package com.zonghong.redpacket.activity.user;

import android.view.View;

import com.waw.hr.mutils.MKey;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.RechargeCashType;
import com.zonghong.redpacket.databinding.ActivityRechargeBinding;
import com.zonghong.redpacket.utils.IntentUtils;

public class RechargeCashActivity extends BaseActivity<ActivityRechargeBinding> {

    private RechargeCashType type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initUI() {
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
        binding.tvCard.setOnClickListener((v) -> {
            IntentUtils.intent2ChooseBankActivity(type);
        });
    }
}

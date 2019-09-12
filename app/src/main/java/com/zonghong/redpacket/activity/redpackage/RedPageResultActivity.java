package com.zonghong.redpacket.activity.redpackage;

import android.view.View;

import com.waw.hr.mutils.MKey;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityRedpackageResultBinding;

public class RedPageResultActivity extends BaseActivity<ActivityRedpackageResultBinding> {

    private String userName, avatar;

    private String money;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_redpackage_result;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        money = getIntent().getExtras().getString(MKey.MONEY);
        binding.tvMoney.setText(money);
    }

    @Override
    public void initListener() {

    }
}

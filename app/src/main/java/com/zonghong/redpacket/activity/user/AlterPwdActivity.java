package com.zonghong.redpacket.activity.user;

import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityAlterPwdBinding;

public class AlterPwdActivity extends BaseActivity<ActivityAlterPwdBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_pwd;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("修改密码");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}

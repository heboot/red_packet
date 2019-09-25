package com.zonghong.redpacket.activity.redpackage;

import android.view.View;
import android.widget.Toast;

import com.example.http.HttpClient;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.waw.hr.mutils.bean.GetRedpackageModel;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityRedpackageResultBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ZhuanZhangResultActivity extends BaseActivity<ActivityRedpackageResultBinding> {


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
        binding.tvGetInfo.setVisibility(View.GONE);
        binding.rvList.setVisibility(View.GONE);
        binding.tvMoney.setText(getIntent().getStringExtra(MKey.DATA));
        binding.tvDesc.setText(getIntent().getStringExtra(MKey.TITLE));
    }

    @Override
    public void initListener() {
        binding.vBack.setOnClickListener((v) -> {
            finish();
        });
        binding.tvRedpackageLog.setOnClickListener((v) -> {
            // TODO: 2019-09-17 去红包日志
        });
    }


}

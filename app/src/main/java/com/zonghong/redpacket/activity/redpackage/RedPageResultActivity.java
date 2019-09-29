package com.zonghong.redpacket.activity.redpackage;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.RedpackageLogAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityRedpackageResultBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

public class RedPageResultActivity extends BaseActivity<ActivityRedpackageResultBinding> {


    private GetRedpackageBean getRedpackageBean;

    private RedpackageLogAdapter redpackageLogAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_redpackage_result;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        getRedpackageBean = (GetRedpackageBean) getIntent().getExtras().get(MKey.DATA);
        binding.tvMoney.setText(getRedpackageBean.getMyGetMoney());
        binding.tvName.setText(getRedpackageBean.getName() + "的红包");
        binding.tvDesc.setText(getRedpackageBean.getDes());
        ImageUtils.showAvatar(getRedpackageBean.getImageUrl(), binding.ivAvatar);
        redpackageLogAdapter = new RedpackageLogAdapter(getRedpackageBean.getList(), false);
        binding.rvList.setAdapter(redpackageLogAdapter);

        binding.tvGetInfo.setText("已领取" + getRedpackageBean.getNumbering() + "/" + getRedpackageBean.getNumber() + "，共" + getRedpackageBean.getSuming() + "/" + getRedpackageBean.getSum() + "元");
    }

    @Override
    public void initListener() {
        binding.vBack.setOnClickListener((v) -> {
            finish();
        });
        binding.tvRedpackageLog.setOnClickListener((v) -> {
            IntentUtils.doIntent(RedpackageLogActivity.class);
        });
    }
}

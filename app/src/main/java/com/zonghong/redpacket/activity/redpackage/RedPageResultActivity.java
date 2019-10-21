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

import java.math.RoundingMode;
import java.text.NumberFormat;

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
        binding.tvMoney.setText(formatDouble(Double.parseDouble(getRedpackageBean.getMyGetMoney())));
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

    public static String formatDouble(double value) {
//        DecimalFormat df = new DecimalFormat("#.00");
//        String str = df.format(value);


        if (value == 0) {
            return "0.00";
        }
        NumberFormat nf = NumberFormat.getNumberInstance();

        nf.setGroupingUsed(false); //关闭分组，显示将不再以千位符分隔

        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.DOWN);

        String result = nf.format(value);

        if (result.indexOf(".") < 1) {
            return result + ".00";
        }

        return nf.format(value);
    }

}

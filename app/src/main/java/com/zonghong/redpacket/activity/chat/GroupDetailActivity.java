package com.zonghong.redpacket.activity.chat;

import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.AlterTextType;
import com.zonghong.redpacket.databinding.ActivityGroupDetailBinding;
import com.zonghong.redpacket.utils.IntentUtils;

public class GroupDetailActivity extends BaseActivity<ActivityGroupDetailBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_detail;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("聊天详情");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.tvGruopName.setOnClickListener((v) -> {
            IntentUtils.intent2AlterTextActivity(AlterTextType.GROUP_NAME, "");
        });
        binding.tvQrcode.setOnClickListener((v) -> {
        });
    }
}

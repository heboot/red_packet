package com.zonghong.redpacket.activity.common;

import android.view.View;

import com.waw.hr.mutils.MKey;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityTextBinding;

import java.util.Map;

public class TextActivity extends BaseActivity<ActivityTextBinding> {

    private Map text;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_text;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {
        text = (Map) getIntent().getExtras().get(MKey.DATA);
        binding.tvContent.setText((String) text.get("content"));
        binding.includeToolbar.tvTitle.setText((String) text.get("title"));
    }

    @Override
    public void initListener() {

    }
}

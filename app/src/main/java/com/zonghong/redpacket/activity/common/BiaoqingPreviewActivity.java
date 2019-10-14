package com.zonghong.redpacket.activity.common;

import android.view.View;

import com.waw.hr.mutils.MKey;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityCustomBiaoqingPreviewBinding;
import com.zonghong.redpacket.utils.ImageUtils;

public class BiaoqingPreviewActivity extends BaseActivity<ActivityCustomBiaoqingPreviewBinding> {

    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_biaoqing_preview;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("预览");
    }

    @Override
    public void initData() {
        url = (String) getIntent().getExtras().get(MKey.DATA);
        if(url.endsWith("gif")){
            ImageUtils.showGif(url,binding.ivImage);
        }else{
            ImageUtils.showImage(url,binding.ivImage);
        }
    }

    @Override
    public void initListener() {

    }
}

package com.zonghong.redpacket.activity.user;

import android.graphics.Bitmap;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityQrcodeBinding;
import com.zonghong.redpacket.service.UserService;

public class QRCodeActivity extends BaseActivity<ActivityQrcodeBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {

        Bitmap mBitmap = CodeUtils.createImage(UserService.getInstance().getUserId(), getResources().getDimensionPixelOffset(R.dimen.x211), getResources().getDimensionPixelOffset(R.dimen.y211), null);
        binding.ivCode.setImageBitmap(mBitmap);
    }

    @Override
    public void initListener() {

    }
}

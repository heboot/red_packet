package com.zonghong.redpacket.activity.user;

import android.graphics.Bitmap;
import android.view.View;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.bean.UserInfoBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.QRCodeType;
import com.zonghong.redpacket.databinding.ActivityQrcodeBinding;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;

public class QRCodeActivity extends BaseActivity<ActivityQrcodeBinding> {

    private QRCodeType qrCodeType;

    private String name, avatar, sex, accountId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {

        qrCodeType = (QRCodeType) getIntent().getExtras().get(MKey.TYPE);
        name = getIntent().getStringExtra(MKey.NAME);
        avatar = getIntent().getStringExtra(MKey.AVATAR);
        sex = getIntent().getStringExtra(MKey.SEX);
        accountId = getIntent().getStringExtra(MKey.ID);


        String qrId = "";
        if (qrCodeType == QRCodeType.USER) {
            qrId = "u" + accountId;
        } else {
            qrId = "g" + accountId;
        }
        Bitmap mBitmap = CodeUtils.createImage(qrId, getResources().getDimensionPixelOffset(R.dimen.x211), getResources().getDimensionPixelOffset(R.dimen.y211), null);
        binding.ivCode.setImageBitmap(mBitmap);

        if (qrCodeType == QRCodeType.GROUP) {
            binding.ivSex.setVisibility(View.GONE);
            binding.tvInfo.setText("该二维码7天内有效，重新进入将更新");
        } else {
            binding.tvInfo.setText("扫描上方二维码，加我好友");
        }

        binding.tvName.setText(name);
        ImageUtils.showAvatar(avatar, binding.ivAvatar);
        if (!StringUtils.isEmpty(sex)) {
            if (sex.equals("1")) {
                binding.ivSex.setBackgroundResource(R.mipmap.icon_sex_man);
            } else {
                binding.ivSex.setBackgroundResource(R.mipmap.icon_sex_woman);
            }
        }

        binding.tvId.setText(accountId);


    }

    @Override
    public void initListener() {

    }
}

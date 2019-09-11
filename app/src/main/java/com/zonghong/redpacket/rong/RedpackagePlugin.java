package com.zonghong.redpacket.rong;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.waw.hr.mutils.LogUtil;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.redpackage.NewRedPackageActivity;
import com.zonghong.redpacket.common.RedPackageType;
import com.zonghong.redpacket.utils.IntentUtils;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

public class RedpackagePlugin implements IPluginModule {
    @Override
    public Drawable obtainDrawable(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_redpackage);
        return drawable;
    }

    @Override
    public String obtainTitle(Context context) {
        return "发红包";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        // TODO: 2019-09-11  去发红包页面
//        LogUtil.e("hongbao", JSON.toJSONString(rongExtension));
        IntentUtils.intent2NewRedPackageActivity(RedPackageType.CHAT);
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}

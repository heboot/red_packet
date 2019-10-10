package com.zonghong.redpacket.rong;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.zonghong.redpacket.R;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.IPluginRequestPermissionResultCallback;
import io.rong.imkit.plugin.location.AMapLocationActivity;
import io.rong.imkit.plugin.location.AMapLocationActivity2D;
import io.rong.imkit.utilities.PermissionCheckUtil;

public class MyLocationPlugin implements IPluginModule, IPluginRequestPermissionResultCallback {
    public MyLocationPlugin() {
    }

    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.rc_ext_plugin_location_selector);
    }

    public String obtainTitle(Context context) {
        return context.getString(R.string.rc_plugin_location);
    }

    public void onClick(Fragment currentFragment, RongExtension extension) {
//        String[] permissions = new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_NETWORK_STATE"};
//        if (PermissionCheckUtil.checkPermissions(currentFragment.getActivity(), permissions)) {
            this.startLocationActivity(currentFragment, extension);
//        } else {
//            extension.requestPermissionForPluginResult(permissions, 255, this);
//        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    private void startLocationActivity(Fragment fragment, RongExtension extension) {
        Intent intent;
        if (extension.getContext().getResources().getBoolean(R.bool.rc_location_2D)) {
            intent = new Intent(fragment.getActivity(), AMapLocationActivity2D.class);
        } else {
            intent = new Intent(fragment.getActivity(), AMapLocationActivity.class);
        }

        extension.startActivityForPluginResult(intent, 1, this);
    }

    public boolean onRequestPermissionResult(Fragment fragment, RongExtension extension, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionCheckUtil.checkPermissions(fragment.getActivity(), permissions)) {
            this.startLocationActivity(fragment, extension);
        } else {
            extension.showRequestPermissionFailedAlter(PermissionCheckUtil.getNotGrantedPermissionMsg(fragment.getActivity(), permissions, grantResults));
        }

        return true;
    }
}
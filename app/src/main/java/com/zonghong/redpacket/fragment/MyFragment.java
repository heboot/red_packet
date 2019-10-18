package com.zonghong.redpacket.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.UserInfoBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.common.ComplaintActivity2;
import com.zonghong.redpacket.activity.common.HelpActivity;
import com.zonghong.redpacket.activity.common.SafeActivity;
import com.zonghong.redpacket.activity.common.TongyongActivity;
import com.zonghong.redpacket.activity.user.InfoActivity;
import com.zonghong.redpacket.activity.user.MyBankActivity;
import com.zonghong.redpacket.activity.common.SettingActivity;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.FragmentMyBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.message.utils.BitmapUtil;
import pub.devrel.easypermissions.EasyPermissions;

public class MyFragment extends BaseFragment<FragmentMyBinding> {

    private int REQUEST_CODE = 988;


    private final int RC_CONTACTS = 999;

    private QMUIDialog permissionDialog;

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.vBack.setVisibility(View.GONE);
        binding.includeToolbar.tvTitle.setText("我的");
        permissionDialog = new QMUIDialog.MessageDialogBuilder(_mActivity)
                .setMessage("系统需要获相机权限")
                .setTitle("提醒")
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        permissionDialog.dismiss();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String[] perms = {Manifest.permission.CAMERA};
                            EasyPermissions.requestPermissions(_mActivity, "系统需要获相机权限",
                                    RC_CONTACTS, perms);
                        } else {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", MAPP.mapp.getApplicationContext().getPackageName(), null);
                            intent.setData(uri);
                            MAPP.mapp.startActivity(intent);
                        }
                    }
                })
                .addAction("退出", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        permissionDialog.dismiss();
                    }
                })
                .create();

    }

    @Override
    public void initData() {
        userInfo();


    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        userInfo();

    }

    @Override
    public void initListener() {
        binding.tvSetting.setOnClickListener((v) -> {
            IntentUtils.doIntent(SettingActivity.class);
        });
        binding.vBg.setOnClickListener((v) -> {
            IntentUtils.doIntent(InfoActivity.class);
        });
        binding.tvCard.setOnClickListener((v) -> {
            IntentUtils.doIntent(MyBankActivity.class);
        });
        binding.tvHelp.setOnClickListener((v) -> {
            IntentUtils.doIntent(HelpActivity.class);
        });
        binding.tvScan.setOnClickListener((v) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!EasyPermissions.hasPermissions(_mActivity, Manifest.permission.CAMERA)) {
                    permissionDialog.show();
                    return;
                }
            }
            Intent intent = new Intent(_mActivity, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
        binding.tvTousu.setOnClickListener(view->{
            IntentUtils.doIntent(ComplaintActivity2.class);
        });
        binding.tvTongyong.setOnClickListener(view->{
            IntentUtils.doIntent(TongyongActivity.class);
        });
        binding.tvYinsi.setOnClickListener(view->{
            IntentUtils.doIntent(SafeActivity.class);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (result.indexOf("u") > -1) {
                        IntentUtils.intent2ContactsDetailActivity(result.substring(1, result.length()), ContactsDetailType.NORMAL);
                    } else if (result.indexOf("g") > -1) {
                        addGroup(result.substring(1));
//                        IntentUtils.intent2ContactsDetailActivity(result.substring(1, result.length()), ContactsDetailType.NORMAL);
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MAPP.mapp, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void addGroup(String groupId) {
        params = new HashMap<>();
        params.put("group_id", groupId);
        HttpClient.Builder.getServer().gAddUser(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private UserInfo userInfo;

    private void userInfo() {
        HttpClient.Builder.getServer().userInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<UserInfoBean>() {
            @Override
            public void onSuccess(BaseBean<UserInfoBean> baseBean) {
                ImageUtils.showAvatar(baseBean.getData().getImg(), binding.ivAvatar);
                binding.tvName.setText(baseBean.getData().getNick_name());
                UserService.getInstance().setUserInfoBean(baseBean.getData());
                if (baseBean.getData().getSex() == 1) {
                    binding.ivSex.setBackgroundResource(R.mipmap.icon_sex_man);
                } else {
                    binding.ivSex.setBackgroundResource(R.mipmap.icon_sex_woman);
                }
                binding.tvNo.setText("简易号"+baseBean.getData().getAccount_id());
                userInfo = new UserInfo(UserService.getInstance().getUserId(), baseBean.getData().getNick_name(), Uri.parse(baseBean.getData().getImg()));
                UserInfo uuu = new UserInfo(UserService.getInstance().getUserId(), baseBean.getData().getNick_name(), Uri.parse(baseBean.getData().getImg()));
                RongIM.getInstance().refreshUserInfoCache(uuu);
                RongIM.getInstance().setCurrentUserInfo(userInfo);
                RongIM.getInstance().setMessageAttachedUserInfo(true);
            }

            @Override
            public void onError(BaseBean<UserInfoBean> baseBean) {

            }
        });
    }
}

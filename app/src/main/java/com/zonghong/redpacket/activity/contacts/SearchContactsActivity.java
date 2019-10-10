package com.zonghong.redpacket.activity.contacts;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.NewContactsAdapter;
import com.zonghong.redpacket.adapter.SearchContactsAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.ActivitySearchContactsBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;

public class SearchContactsActivity extends BaseActivity<ActivitySearchContactsBinding> {

    private SearchContactsAdapter searchContactsAdapter;

    private int REQUEST_CODE = 988;
    private final int RC_CONTACTS = 999;
    private QMUIDialog permissionDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_contacts;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("添加朋友");
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
        binding.tvMyNo.setText("我的简易号：" + UserService.getInstance().getUserInfoBean().getAccount_id());
        permissionDialog = new QMUIDialog.MessageDialogBuilder(this)
                .setMessage("系统需要获相机权限")
                .setTitle("提醒")
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        permissionDialog.dismiss();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String[] perms = {Manifest.permission.CAMERA};
                            EasyPermissions.requestPermissions(SearchContactsActivity.this, "系统需要获相机权限",
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
    public void initListener() {
        binding.etKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });
        binding.etKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s) || s.length() == 0) {
                    binding.rvList.setVisibility(View.GONE);
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.tvScan.setOnClickListener((v) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
                    permissionDialog.show();
                    return;
                }
            }
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
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
                tipDialog = DialogUtils.getSuclDialog(SearchContactsActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(SearchContactsActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private void search() {
        if (StringUtils.isEmpty(binding.etKey.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入ID或昵称", true);
            tipDialog.show();
            return;
        }


        loadingDialog.show();

        params.put(MKey.SEARCH_CONTENT, binding.etKey.getText());

        HttpClient.Builder.getServer().fAdd(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<SearchContatsBean>>() {
            @Override
            public void onSuccess(BaseBean<List<SearchContatsBean>> baseBean) {
                loadingDialog.dismiss();
                if (baseBean.getData() == null || baseBean.getData().size() == 0) {
                    tipDialog = DialogUtils.getFailDialog(SearchContactsActivity.this, baseBean.getMsg(), true);
                    tipDialog.show();
                    return;
                }
                binding.rvList.setVisibility(View.VISIBLE);
                if (searchContactsAdapter == null) {
                    if (baseBean.getData() != null) {
                        searchContactsAdapter = new SearchContactsAdapter(baseBean.getData());
                        binding.rvList.setAdapter(searchContactsAdapter);
                    }
                } else {
                    searchContactsAdapter.getData().clear();
                    searchContactsAdapter.setNewData(baseBean.getData());
                    searchContactsAdapter.notifyDataSetChanged();
                }
//                IntentUtils.intent2ContactsDetailActivity(baseBean.getData().get(0));
            }

            @Override
            public void onError(BaseBean<List<SearchContatsBean>> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(SearchContactsActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}

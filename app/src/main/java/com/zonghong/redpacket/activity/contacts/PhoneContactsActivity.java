package com.zonghong.redpacket.activity.contacts;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsListBean;
import com.waw.hr.mutils.model.ContactsModel;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.phonecontacts.PhoneContactsContainerAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.FragmentContactsBinding;
import com.zonghong.redpacket.databinding.LayoutSearchBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ContactsUtils;
import com.zonghong.redpacket.utils.SearchUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;

public class PhoneContactsActivity extends BaseActivity<FragmentContactsBinding> implements EasyPermissions.PermissionCallbacks {

    private PhoneContactsContainerAdapter contactsAdapter;

    private final int RC_CONTACTS = 996;

    private QMUIDialog permissionDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.vBack.setVisibility(View.GONE);
        binding.includeToolbar.tvTitle.setText("通讯录");
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }

    @Override
    public void initData() {

        permissionDialog = new QMUIDialog.MessageDialogBuilder(this)
                .setMessage("系统需要获取通讯录权限")
                .setTitle("提醒")
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        permissionDialog.dismiss();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String[] perms = {Manifest.permission.READ_CONTACTS};
                            EasyPermissions.requestPermissions(PhoneContactsActivity.this, "需要获取通讯录权限",
                                    RC_CONTACTS, perms);
                        } else {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", MAPP.mapp.getApplicationContext().getPackageName(), null);
                            intent.setData(uri);
                            PhoneContactsActivity.this.startActivity(intent);
                        }
                    }
                })
                .addAction("退出", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        permissionDialog.dismiss();
                        finish();
                    }
                })
                .create();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!EasyPermissions.hasPermissions(this, Manifest.permission.READ_CONTACTS)) {
                permissionDialog.show();
                return;
            }
        } else {
            boolean result = false;
            try {
                result = ContactsUtils.checkReadContacts(this);
            } catch (Exception e) {
                result = false;
            }
            if (!result) {
                permissionDialog.show();
                return;
            }
        }


        list();
    }

    @Override
    public void initListener() {

    }

    private void list() {
        params = new HashMap<>();
//        List list = new ArrayList();
//
//        ContactsModel contactsModel = new ContactsModel();
//        contactsModel.setPhone("13783400213");
//        contactsModel.setPhoneName("哈哈");
//
//        ContactsModel contactsModel2 = new ContactsModel();
//        contactsModel2.setPhone("18538523856");
//        contactsModel2.setPhoneName("哈哈2");
//
//        list.add(contactsModel);
//        list.add(contactsModel2);
        params.put("addressList", JSON.toJSONString(JSON.toJSONString(ContactsUtils.getAllContacts(this))));// JSON.toJSONString(ContactsUtils.getAllContacts(this))
        HttpClient.Builder.getServer().addressList(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<ContatsListBean>() {
            @Override
            public void onSuccess(BaseBean<ContatsListBean> baseBean) {
                if (contactsAdapter == null) {
                    if (baseBean.getData().getList() != null) {
                        contactsAdapter = new PhoneContactsContainerAdapter(baseBean.getData().getList(), false);
                        contactsAdapter.addHeaderView(getSearchView());
                        binding.rvList.setAdapter(contactsAdapter);
                    }
                } else {
                    contactsAdapter.getData().clear();
                    contactsAdapter.setNewData(baseBean.getData().getList());
                    contactsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(BaseBean<ContatsListBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(PhoneContactsActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }



    private View getSearchView() {
        LayoutSearchBinding layoutSearchBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.layout_search, null, false);
//        layoutSearchBinding.etKey.setOnClickListener((v) -> {
//            IntentUtils.doIntent(SearchContactsActivity.class);
//        });
        layoutSearchBinding.etKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence) || charSequence.length() == 0) {
                    list();
                    return;
                }
                contactsAdapter.setNewData(SearchUtils.searchContacts(contactsAdapter.getData(), charSequence.toString()));
                contactsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        layoutSearchBinding.getRoot().setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.y55)));
        return layoutSearchBinding.getRoot();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        ToastUtils.show(MAPP.mapp, "grant");
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        ToastUtils.show(MAPP.mapp, "denied");
        permissionDialog.show();
    }
}

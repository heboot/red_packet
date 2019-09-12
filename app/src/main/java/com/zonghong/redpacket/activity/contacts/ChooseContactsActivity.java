package com.zonghong.redpacket.activity.contacts;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.ObserableUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.waw.hr.mutils.bean.ContatsFriendBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.loginregister.RegisterActivity;
import com.zonghong.redpacket.adapter.ContactsContainerAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.FragmentContactsBinding;
import com.zonghong.redpacket.databinding.LayoutSearchBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChooseContactsActivity extends BaseActivity<FragmentContactsBinding> {

    private ContactsContainerAdapter contactsAdapter;

    private String checkIds = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvRight.setVisibility(View.VISIBLE);
        binding.includeToolbar.tvRight.setText("完成");

        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    public void initData() {
        list();

    }

    private View getSearchView() {
        LayoutSearchBinding layoutSearchBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.layout_search, null, false);
        layoutSearchBinding.etKey.setOnClickListener((v) -> {
            // TODO: 2019-09-10 goto search
        });
        return layoutSearchBinding.getRoot();
    }


    private void list() {

        HttpClient.Builder.getServer().fIndex(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<ContatsChildBean>>() {
            @Override
            public void onSuccess(BaseBean<List<ContatsChildBean>> baseBean) {
                if (contactsAdapter == null) {
                    if (baseBean.getData() != null) {
                        contactsAdapter = new ContactsContainerAdapter(baseBean.getData(), true);
                        contactsAdapter.addHeaderView(getSearchView());
                        binding.rvList.setAdapter(contactsAdapter);
                    }
                } else {
                    contactsAdapter.getData().clear();
                    contactsAdapter.setNewData(baseBean.getData());
                    contactsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(BaseBean<List<ContatsChildBean>> baseBean) {
                tipDialog = DialogUtils.getFailDialog(ChooseContactsActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    @Override
    public void initListener() {
        binding.includeToolbar.tvRight.setOnClickListener((v) -> {
            checkIds = "";
            for (ContatsChildBean contatsChildBean : contactsAdapter.getData()) {
                for (ContatsFriendBean friendBean : contatsChildBean.getList()) {
                    if (friendBean.isCheck()) {
                        checkIds = checkIds + friendBean.getID() + ",";
                    }
                }
            }

            if (StringUtils.isEmpty(checkIds)) {
                tipDialog = DialogUtils.getFailDialog(this, "请先选择好友", true);
                tipDialog.show();
                return;
            }
            checkIds = checkIds + UserService.getInstance().getUserId();

            newgroup();
        });
    }


    private void newgroup() {
        params.put("user_list", checkIds);
        HttpClient.Builder.getServer().gCreate(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                RongUtils.toGroupChat(baseBean.getData().toString(), "群聊");
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(ChooseContactsActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}

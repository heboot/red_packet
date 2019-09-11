package com.zonghong.redpacket.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ChooseContactsActivity;
import com.zonghong.redpacket.adapter.ContactsAdapter;
import com.zonghong.redpacket.adapter.ContactsContainerAdapter;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.databinding.FragmentContactsBinding;
import com.zonghong.redpacket.databinding.LayoutContactsHeadBinding;
import com.zonghong.redpacket.databinding.LayoutSearchBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactsFragment extends BaseFragment<FragmentContactsBinding> {

    private ContactsContainerAdapter contactsAdapter;

    public static ContactsFragment newInstance() {
        Bundle args = new Bundle();
        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    public void initUI() {
        binding.rvList.setLayoutManager(new LinearLayoutManager(_mActivity, RecyclerView.VERTICAL, false));
    }

    @Override
    public void initData() {
        list();
    }

    private void list() {

        HttpClient.Builder.getServer().fIndex(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<ContatsChildBean>>() {
            @Override
            public void onSuccess(BaseBean<List<ContatsChildBean>> baseBean) {
                if (contactsAdapter == null) {
                    if (baseBean.getData() != null) {
                        contactsAdapter = new ContactsContainerAdapter(baseBean.getData(), false);
                        contactsAdapter.addHeaderView(getSearchView());
                        contactsAdapter.addHeaderView(getContactsHeadView());
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
                tipDialog = DialogUtils.getFailDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private View getSearchView() {
        LayoutSearchBinding layoutSearchBinding = DataBindingUtil.inflate(LayoutInflater.from(_mActivity), R.layout.layout_search, null, false);
        layoutSearchBinding.etKey.setOnClickListener((v) -> {
            // TODO: 2019-09-10 goto search
        });
        return layoutSearchBinding.getRoot();
    }

    private View getContactsHeadView() {
        LayoutContactsHeadBinding layoutContactsHeadBinding = DataBindingUtil.inflate(LayoutInflater.from(_mActivity), R.layout.layout_contacts_head, null, false);
        return layoutContactsHeadBinding.getRoot();
    }


    @Override
    public void initListener() {

    }
}

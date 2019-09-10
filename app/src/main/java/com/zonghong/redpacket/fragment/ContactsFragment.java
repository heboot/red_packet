package com.zonghong.redpacket.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.ContactsAdapter;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.databinding.FragmentContactsBinding;
import com.zonghong.redpacket.databinding.LayoutContactsHeadBinding;
import com.zonghong.redpacket.databinding.LayoutSearchBinding;

import java.util.ArrayList;

public class ContactsFragment extends BaseFragment<FragmentContactsBinding> {

    private ContactsAdapter contactsAdapter;

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
        ArrayList arrayList = new ArrayList();
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        arrayList.add("1");
        contactsAdapter = new ContactsAdapter(arrayList);
        contactsAdapter.addHeaderView(getSearchView());
        contactsAdapter.addHeaderView(getContactsHeadView());
        binding.rvList.setAdapter(contactsAdapter);
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

package com.zonghong.redpacket.fragment;

import android.os.Bundle;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.redpackage.NewRedPackageActivity;
import com.zonghong.redpacket.adapter.ContactsAdapter;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.databinding.FragmentMyBinding;
import com.zonghong.redpacket.utils.IntentUtils;

public class WalletFragment extends BaseFragment<FragmentMyBinding> {


    public static WalletFragment newInstance() {
        Bundle args = new Bundle();
        WalletFragment fragment = new WalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initUI() {
    }

    @Override
    public void initData() {

    }


    @Override
    public void initListener() {
        binding.getRoot().setOnClickListener((v) -> {
            IntentUtils.doIntent(NewRedPackageActivity.class);
        });
    }
}

package com.zonghong.redpacket.fragment;

import android.os.Bundle;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.redpackage.NewRedPackageActivity;
import com.zonghong.redpacket.adapter.ContactsAdapter;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.common.RechargeCashType;
import com.zonghong.redpacket.databinding.FragmentMyBinding;
import com.zonghong.redpacket.databinding.FragmentWalletBinding;
import com.zonghong.redpacket.utils.IntentUtils;

public class WalletFragment extends BaseFragment<FragmentWalletBinding> {


    public static WalletFragment newInstance() {
        Bundle args = new Bundle();
        WalletFragment fragment = new WalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    public void initUI() {
    }

    @Override
    public void initData() {

    }


    @Override
    public void initListener() {
        binding.tvCash.setOnClickListener((v) -> {
            IntentUtils.intent2RechargeCaseActivity(RechargeCashType.CASH);
        });
        binding.tvRecharge.setOnClickListener((v) -> {
            IntentUtils.intent2RechargeCaseActivity(RechargeCashType.RECHARGE);
        });
    }

}

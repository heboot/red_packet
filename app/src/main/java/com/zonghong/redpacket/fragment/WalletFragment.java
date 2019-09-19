package com.zonghong.redpacket.fragment;

import android.os.Bundle;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.redpackage.NewRedPackageActivity;
import com.zonghong.redpacket.adapter.ContactsAdapter;
import com.zonghong.redpacket.adapter.ContactsContainerAdapter;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.common.RechargeCashType;
import com.zonghong.redpacket.databinding.FragmentMyBinding;
import com.zonghong.redpacket.databinding.FragmentWalletBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    public void onSupportVisible() {
        super.onSupportVisible();
        balance();
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

    private void balance() {

        HttpClient.Builder.getServer().bRead(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Map>() {
            @Override
            public void onSuccess(BaseBean<Map> baseBean) {
                binding.tvBalance.setText((double) baseBean.getData().get("balance") + "");

            }

            @Override
            public void onError(BaseBean<Map> baseBean) {
                tipDialog = DialogUtils.getFailDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}

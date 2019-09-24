package com.zonghong.redpacket.activity.user;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.BalanceLogBean;
import com.waw.hr.mutils.bean.BalanceLogListBean;
import com.waw.hr.mutils.bean.CashListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.BalanceLogAdapter;
import com.zonghong.redpacket.adapter.CashLogAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityCashLogBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BalanceListActivity extends BaseActivity<ActivityCashLogBinding> {

    private BalanceLogAdapter balanceLogAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cash_log;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("余额明细");
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        cashList();
    }

    @Override
    public void initListener() {

    }


    private void cashList() {
        loadingDialog.show();
        params = new HashMap<>();
        HttpClient.Builder.getServer().bIndex(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<BalanceLogListBean>() {
            @Override
            public void onSuccess(BaseBean<BalanceLogListBean> baseBean) {
                loadingDialog.dismiss();
                if (balanceLogAdapter == null) {
                    if (baseBean.getData() != null) {
                        balanceLogAdapter = new BalanceLogAdapter(baseBean.getData().getList());
                        binding.rvList.setAdapter(balanceLogAdapter);
                    }
                } else {
                    balanceLogAdapter.getData().clear();
                    balanceLogAdapter.setNewData(baseBean.getData().getList());
                    balanceLogAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(BaseBean<BalanceLogListBean> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(BalanceListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}

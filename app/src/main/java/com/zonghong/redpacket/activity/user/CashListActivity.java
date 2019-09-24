package com.zonghong.redpacket.activity.user;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.CashListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.CashLogAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityCashLogBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CashListActivity extends BaseActivity<ActivityCashLogBinding> {

    private CashLogAdapter cashLogAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cash_log;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("提现记录");
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
        HttpClient.Builder.getServer().bEmHistory(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<CashListBean>() {
            @Override
            public void onSuccess(BaseBean<CashListBean> baseBean) {
                loadingDialog.dismiss();
                if (cashLogAdapter == null) {
                    if (baseBean.getData() != null) {
                        cashLogAdapter = new CashLogAdapter(baseBean.getData().getList());
                        binding.rvList.setAdapter(cashLogAdapter);
                    }
                } else {
                    cashLogAdapter.getData().clear();
                    cashLogAdapter.setNewData(baseBean.getData().getList());
                    cashLogAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(BaseBean<CashListBean> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(CashListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}
package com.zonghong.redpacket.activity.user;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.BalanceLogBean;
import com.waw.hr.mutils.bean.BalanceLogListBean;
import com.waw.hr.mutils.bean.CashListBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.BalanceLogAdapter;
import com.zonghong.redpacket.adapter.CashLogAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityCashLogBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.ArrayList;
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
        page = 1;
        balanceLogAdapter = new BalanceLogAdapter(new ArrayList<>());
        balanceLogAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (page + 1 >= total) {
                    balanceLogAdapter.loadMoreComplete();
                    ToastUtils.show(MAPP.mapp, "已经是最后一页了");
                    return;
                }
                page = page + 1;
                cashList();
            }
        }, binding.rvList);
        binding.rvList.setAdapter(balanceLogAdapter);
        cashList();
    }

    @Override
    public void initListener() {
        binding.mrv.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                page = 1;
                cashList();
            }
        });

    }


    private void cashList() {
//        loadingDialog.show();
        params = new HashMap<>();
        params.put("p", page);
        HttpClient.Builder.getServer().bIndex(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<BalanceLogListBean>() {
            @Override
            public void onSuccess(BaseBean<BalanceLogListBean> baseBean) {
                binding.mrv.finishRefresh();
                balanceLogAdapter.loadMoreComplete();
                loadingDialog.dismiss();
                total = baseBean.getData().getTotoalPage();
//                if (balanceLogAdapter == null) {
//                    if (baseBean.getData() != null) {
//                        balanceLogAdapter = new BalanceLogAdapter(baseBean.getData().getList());
//                        balanceLogAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//                            @Override
//                            public void onLoadMoreRequested() {
//                                if (page + 1 > total) {
//                                    ToastUtils.show(MAPP.mapp, "已经是最后一页了");
//                                    return;
//                                }
//                                page = page + 1;
//                                cashList();
//                            }
//                        }, binding.rvList);
//                        binding.rvList.setAdapter(balanceLogAdapter);
//                    }
//                } else {
                balanceLogAdapter.getData().clear();
                balanceLogAdapter.setNewData(baseBean.getData().getList());
                balanceLogAdapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onError(BaseBean<BalanceLogListBean> baseBean) {
                binding.mrv.finishRefresh();
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(BalanceListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}

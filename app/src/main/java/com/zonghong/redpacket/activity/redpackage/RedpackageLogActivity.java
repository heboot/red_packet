package com.zonghong.redpacket.activity.redpackage;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.RedpackageLogBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.RedpackageLogListAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityRedpackageLogBinding;
import com.zonghong.redpacket.databinding.HeadRedpackageLogBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RedpackageLogActivity extends BaseActivity<ActivityRedpackageLogBinding> {

    private int sign = 1;

    private QMUIBottomSheet filterSheet;

    private RedpackageLogListAdapter redpackageLogAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_redpackage_log;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        page = 1;
//        binding.tvName.setText(UserService.getInstance().getUserInfoBean().getNick_name() + "收到的红包");
//        ImageUtils.showAvatar(UserService.getInstance().getUserInfoBean().getImg(), binding.ivAvatar);
        list();
    }

    private HeadRedpackageLogBinding headRedpackageLogBinding;

    private View getHeadView() {
        headRedpackageLogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.head_redpackage_log, null, false);
        headRedpackageLogBinding.tvName.setText(UserService.getInstance().getUserInfoBean().getNick_name() + "收到的红包");
        ImageUtils.showAvatar(UserService.getInstance().getUserInfoBean().getImg(), headRedpackageLogBinding.ivAvatar);
        headRedpackageLogBinding.tvRedpackageLog.setOnClickListener((v) -> {
            if (filterSheet == null) {
                filterSheet = DialogUtils.getRedpackageLogSheet(this, new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        filterSheet.dismiss();
                        if (position == 0) {
                            sign = 1;
                            headRedpackageLogBinding.tvName.setText(UserService.getInstance().getUserInfoBean().getNick_name() + "收到的红包");
                        } else {
                            sign = 2;
                            headRedpackageLogBinding.tvName.setText(UserService.getInstance().getUserInfoBean().getNick_name() + "发出的红包");
                        }
                        list();
                    }
                });
            }
            filterSheet.show();
        });
        return headRedpackageLogBinding.getRoot();
    }

    @Override
    public void initListener() {

    }


    private void list() {
        params = new HashMap<>();
        params.put("sign", sign);
        params.put("p", page);
        HttpClient.Builder.getServer().rList(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<RedpackageLogBean>() {
            @Override
            public void onSuccess(BaseBean<RedpackageLogBean> baseBean) {

                if (redpackageLogAdapter == null) {
                    if (baseBean.getData().getList() != null) {
                        redpackageLogAdapter = new RedpackageLogListAdapter(baseBean.getData().getList());
                        redpackageLogAdapter.addHeaderView(getHeadView());
                        redpackageLogAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                            @Override
                            public void onLoadMoreRequested() {
                                if (page + 1 >= baseBean.getData().getTotalPage()) {
                                    ToastUtils.show(MAPP.mapp, "到底了");
                                    redpackageLogAdapter.loadMoreEnd();
                                    return;
                                }
                                page = page + 1;
                                list();
                            }
                        }, binding.rvList);
                        binding.rvList.setAdapter(redpackageLogAdapter);
                    }
                } else {

                    redpackageLogAdapter.getData().clear();
                    redpackageLogAdapter.addData(baseBean.getData().getList());
                    redpackageLogAdapter.notifyDataSetChanged();
                }
                if (headRedpackageLogBinding != null) {
                    headRedpackageLogBinding.tvMoney.setText(baseBean.getData().getTotalMoney() + "");
                }
                redpackageLogAdapter.loadMoreComplete();

            }

            @Override
            public void onError(BaseBean<RedpackageLogBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(RedpackageLogActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}

package com.zonghong.redpacket.activity.redpackage;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.RedpackageLogBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.RedpackageLogListAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityRedpackageLogBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;

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
        binding.tvName.setText(UserService.getInstance().getUserInfoBean().getNick_name() + "收到的红包");
        ImageUtils.showAvatar(UserService.getInstance().getUserInfoBean().getImg(), binding.ivAvatar);
        list();
    }

    @Override
    public void initListener() {
        binding.tvRedpackageLog.setOnClickListener((v) -> {
            if (filterSheet == null) {
                filterSheet = DialogUtils.getRedpackageLogSheet(this, new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        filterSheet.dismiss();
                        if (position == 0) {
                            sign = 1;
                            binding.tvName.setText(UserService.getInstance().getUserInfoBean().getNick_name() + "收到的红包");
                        } else {
                            sign = 2;
                            binding.tvName.setText(UserService.getInstance().getUserInfoBean().getNick_name() + "发出的红包");
                        }
                        list();
                    }
                });
            }
            filterSheet.show();
        });
    }


    private void list() {
        params.put("sign", sign);
        HttpClient.Builder.getServer().rList(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<RedpackageLogBean>() {
            @Override
            public void onSuccess(BaseBean<RedpackageLogBean> baseBean) {
                if (redpackageLogAdapter == null) {
                    if (baseBean.getData().getList() != null) {
                        redpackageLogAdapter = new RedpackageLogListAdapter(baseBean.getData().getList());
                        binding.rvList.setAdapter(redpackageLogAdapter);
                    }
                } else {
                    redpackageLogAdapter.getData().clear();
                    redpackageLogAdapter.setNewData(baseBean.getData().getList());
                    redpackageLogAdapter.notifyDataSetChanged();
                }
                binding.tvMoney.setText(baseBean.getData().getTotalMoney() + "");

            }

            @Override
            public void onError(BaseBean<RedpackageLogBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(RedpackageLogActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}

package com.zonghong.redpacket.activity.common;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.HelpBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.common.HelpAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityHelpBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HelpActivity extends BaseActivity<ActivityHelpBinding> {

    private HelpAdapter topAdapter, bottomAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("帮助");
        binding.rvTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvBottom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        help();
    }

    @Override
    public void initListener() {

    }

    public void help() {
        HttpClient.Builder.getServer().iHelp(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<HelpBean>() {
            @Override
            public void onSuccess(BaseBean<HelpBean> baseBean) {
                topAdapter = new HelpAdapter((List<Map>) baseBean.getData().getTop());
                bottomAdapter = new HelpAdapter((List<Map>) baseBean.getData().getBottom());
                binding.rvTop.setAdapter(topAdapter);
                binding.rvBottom.setAdapter(bottomAdapter);
            }

            @Override
            public void onError(BaseBean<HelpBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(HelpActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}

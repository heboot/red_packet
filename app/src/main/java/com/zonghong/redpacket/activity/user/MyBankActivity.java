package com.zonghong.redpacket.activity.user;

import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.BankListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.ContactsContainerAdapter;
import com.zonghong.redpacket.adapter.MyBankAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityMyBankBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyBankActivity extends BaseActivity<ActivityMyBankBinding> {

    private MyBankAdapter myBankAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_bank;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("我的银行卡");
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
    }

    @Override
    public void initData() {
        bankList();
    }

    @Override
    public void initListener() {
        binding.tvSubmit.setOnClickListener((v) -> {
            IntentUtils.doIntent(NewBankActivity.class);
        });
    }


    private void bankList() {
        loadingDialog.show();
        HttpClient.Builder.getServer().bankList(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<BankListBean>>() {
            @Override
            public void onSuccess(BaseBean<List<BankListBean>> baseBean) {
                loadingDialog.dismiss();
                if (myBankAdapter == null) {
                    if (baseBean.getData() != null) {
                        myBankAdapter = new MyBankAdapter(baseBean.getData());
                        binding.rvList.setAdapter(myBankAdapter);
                    }
                } else {
                    myBankAdapter.getData().clear();
                    myBankAdapter.setNewData(baseBean.getData());
                    myBankAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(BaseBean<List<BankListBean>> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(MyBankActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}

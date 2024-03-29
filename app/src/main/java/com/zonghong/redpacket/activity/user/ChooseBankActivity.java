package com.zonghong.redpacket.activity.user;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.BankListBean;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.ChooseBankAdapter;
import com.zonghong.redpacket.adapter.MyBankAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityChooseBankBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChooseBankActivity extends BaseActivity<ActivityChooseBankBinding> {

    private ChooseBankAdapter chooseBankAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_bank;
    }

    @Override
    public void initUI() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
        binding.includeToolbar.tvTitle.setText("选择充值方式");//根据枚举类型显示不同的标题
        setBackVisibility(View.VISIBLE);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        bankList();
    }

    @Override
    public void initListener() {

    }

    private void bankList() {
        loadingDialog.show();
        HttpClient.Builder.getServer().bankList(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<BankListBean>>() {
            @Override
            public void onSuccess(BaseBean<List<BankListBean>> baseBean) {
                loadingDialog.dismiss();
                if (chooseBankAdapter == null) {
                    if (baseBean.getData() != null) {
                        chooseBankAdapter = new ChooseBankAdapter(baseBean.getData(), new WeakReference<>(ChooseBankActivity.this));
                        binding.rvList.setAdapter(chooseBankAdapter);
                    }
                } else {
                    chooseBankAdapter.getData().clear();
                    chooseBankAdapter.setNewData(baseBean.getData());
                    chooseBankAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(BaseBean<List<BankListBean>> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(ChooseBankActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    public void chooseBank(BankListBean bankListBean) {
        EventBus.getDefault().postSticky(new UserEvent.CHOOSE_BANK_EVENT(bankListBean));
        finish();
    }
}

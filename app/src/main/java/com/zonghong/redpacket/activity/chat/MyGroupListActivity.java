package com.zonghong.redpacket.activity.chat;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.MyGroupBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.group.MyGroupAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityNewContactsListBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyGroupListActivity extends BaseActivity<ActivityNewContactsListBinding> {

    private MyGroupAdapter myGroupAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_contacts_list;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("我的群聊");
        setBackVisibility(View.VISIBLE);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        list();
    }

    @Override
    public void initListener() {

    }


    private void list() {
        HttpClient.Builder.getServer().gIndex(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<MyGroupBean>>() {
            @Override
            public void onSuccess(BaseBean<List<MyGroupBean>> baseBean) {
                if (myGroupAdapter == null) {
                    if (baseBean.getData() != null) {
                        myGroupAdapter = new MyGroupAdapter(baseBean.getData());
                        binding.rvList.setAdapter(myGroupAdapter);
                    }
                } else {
                    myGroupAdapter.getData().clear();
                    myGroupAdapter.setNewData(baseBean.getData());
                    myGroupAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(BaseBean<List<MyGroupBean>> baseBean) {
                tipDialog = DialogUtils.getFailDialog(MyGroupListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}

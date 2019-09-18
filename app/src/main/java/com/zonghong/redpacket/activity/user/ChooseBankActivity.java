package com.zonghong.redpacket.activity.user;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.ChooseBankAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityChooseBankBinding;

import java.util.ArrayList;

public class ChooseBankActivity extends BaseActivity<ActivityChooseBankBinding> {

    private ChooseBankAdapter chooseBankAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_bank;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("选择充值方式");//根据枚举类型显示不同的标题
        setBackVisibility(View.VISIBLE);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        ArrayList list = new ArrayList();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        chooseBankAdapter = new ChooseBankAdapter(list);
        binding.rvList.setAdapter(chooseBankAdapter);
    }

    @Override
    public void initListener() {

    }
}

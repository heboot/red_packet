package com.zonghong.redpacket.rong;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.CustomBiaoqingListBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.CustomBiaoqingAdapter;
import com.zonghong.redpacket.databinding.LayoutCustomBiaoqingBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.emoticon.IEmoticonTab;

public class MyEmoticon implements IEmoticonTab {

    private CustomBiaoqingAdapter customBiaoqingAdapter;

    private CustomBiaoqingListBean.ExpressionListBean expressionListBean;

    private LayoutCustomBiaoqingBinding binding;

    private List<CustomBiaoqingListBean.ExpressionListBean> datas = new ArrayList<>();

    @Override
    public Drawable obtainTabDrawable(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_custom_bq);
        return drawable;
    }

    @Override
    public View obtainTabPager(Context context) {
        expressionListBean = new CustomBiaoqingListBean.ExpressionListBean();
        expressionListBean.setAdd(true);

        binding = DataBindingUtil.inflate(LayoutInflater.from(MAPP.mapp), R.layout.layout_custom_biaoqing, null, false);
        binding.rvList.setLayoutManager(new GridLayoutManager(MAPP.mapp,4));
        list();
        return binding.getRoot();
    }

    @Override
    public void onTableSelected(int i) {

    }


    private void list() {
        HttpClient.Builder.getServer().getExpression(UserService.getInstance().getToken(), new HashMap<>()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<CustomBiaoqingListBean.ExpressionListBean>>() {
            @Override
            public void onSuccess(BaseBean<List<CustomBiaoqingListBean.ExpressionListBean>> baseBean) {
                datas.clear();
                datas.add(expressionListBean);
                if (baseBean.getData() != null   && baseBean.getData().size() > 0) {
                    datas.addAll(baseBean.getData());
                }
                if (customBiaoqingAdapter == null) {
                        customBiaoqingAdapter = new CustomBiaoqingAdapter(datas);
                        binding.rvList.setAdapter(customBiaoqingAdapter);
                } else {
                    customBiaoqingAdapter.setNewData(datas);
                }

            }

            @Override
            public void onError(BaseBean<List<CustomBiaoqingListBean.ExpressionListBean>> baseBean) {
            }
        });
    }


}

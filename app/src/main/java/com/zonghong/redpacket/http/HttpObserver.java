package com.zonghong.redpacket.http;


import com.alibaba.fastjson.JSON;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.MCode;
import com.waw.hr.mutils.NetWorkUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.MAPP;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by heboot on 2018/1/17.
 */

public abstract class HttpObserver<T> implements Observer<BaseBean<T>> {

    private String TAG = this.getClass().getName();

    private Disposable disposable;


    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
        if (!NetWorkUtils.isConnectedByState(MAPP.mapp)) {
            onComplete();
            this.disposable.dispose();
            return;
        }
//        httpCallBack.onSubscribe(d);
    }


    @Override
    public void onNext(BaseBean<T> baseBean) {
        LogUtil.e(TAG, JSON.toJSONString(baseBean));
        if (baseBean.getCode() != MCode.HTTP_CODE.SUCCESS || baseBean == null) {
            if (baseBean.getCode() == MCode.HTTP_CODE.TOKEN_ERROR) {
//                IntentUtils.doIntent(LoginActivity.class);
            }
            this.disposable.dispose();
            try {
                onError(baseBean);
            } catch (Exception ex) {
                onError(ex);
            }
            return;
        }
        onSuccess(baseBean);
    }

    public abstract void onSuccess(BaseBean<T> baseBean);

    public abstract void onError(BaseBean<T> baseBean);


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
        this.disposable.dispose();
    }
}

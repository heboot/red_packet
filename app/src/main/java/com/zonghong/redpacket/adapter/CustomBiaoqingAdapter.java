package com.zonghong.redpacket.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.bean.CustomBiaoqingListBean;
import com.waw.hr.mutils.event.MessageEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.chat.CustomBiaoqingManagerActivity;
import com.zonghong.redpacket.databinding.ItemCustomBiaoqingBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;

public class CustomBiaoqingAdapter extends BaseQuickAdapter<CustomBiaoqingListBean.ExpressionListBean, BaseViewHolder> {

    private WeakReference<CustomBiaoqingManagerActivity> weakReference;

    public CustomBiaoqingAdapter(@Nullable List<CustomBiaoqingListBean.ExpressionListBean> data) {
        super(R.layout.item_custom_biaoqing, data);
    }

    public CustomBiaoqingAdapter(@Nullable List<CustomBiaoqingListBean.ExpressionListBean> data, WeakReference<CustomBiaoqingManagerActivity> weakReference) {
        super(R.layout.item_custom_biaoqing, data);
        this.weakReference = weakReference;
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomBiaoqingListBean.ExpressionListBean item) {
        ItemCustomBiaoqingBinding binding = DataBindingUtil.bind(helper.itemView);
        if (item.isAdd()) {
            //显示加号
            ImageUtils.showImage(R.mipmap.icon_add_bq, binding.ivImg);
        } else {
            ImageUtils.showBiaoqing(item.getImage(), binding.ivImg);
        }

        binding.getRoot().setOnClickListener(view -> {
            if (item.isAdd()) {
                if (weakReference == null || weakReference.get() == null) {
                    IntentUtils.doIntent(CustomBiaoqingManagerActivity.class);
                } else {
                    weakReference.get().selectPic();
                }

            } else {
                if(weakReference == null || weakReference.get() == null){
//                    RongUtils.sendCustonBiaoqingMessage();
//                    LogUtil.e("点击了1>>>" ,binding.ivImg.getBackground()+"");
                    LogUtil.e("点击了2>>>" ,binding.ivImg.getDrawable()+"");
                    EventBus.getDefault().post(new MessageEvent.SEND_CUSTOM_BIAOQING_EVENT(item,binding.ivImg.getDrawable()));
                }
            }

        });

    }
}

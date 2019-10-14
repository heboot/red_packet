package com.zonghong.redpacket.rong;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.CustomBiaoqingBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.MessageCustomBiaoqingBinding;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import io.rong.imageloader.core.ImageLoader;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.utils.BitmapUtil;

@ProviderTag(
        messageContent = CustomBiaoqingMessage.class,
        showProgress = false,
        showReadState = false
)
public class CustomBiaoqingMessageView extends IContainerItemProvider.MessageProvider<CustomBiaoqingMessage> {

    private static final String TAG = "ImageMessageItemProvider";

    public CustomBiaoqingMessageView() {
    }

    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_custom_biaoqing, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.x120), MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.y100)));
        return view;
    }

    public void onItemClick(View view, int position, CustomBiaoqingMessage content, UIMessage message) {
        if (content != null) {
//            Intent intent = new Intent("io.rong.imkit.intent.action.picturepagerview");
//            intent.setPackage(view.getContext().getPackageName());
//            intent.putExtra("message", message.getMessage());
//            view.getContext().startActivity(intent);
            String s = new String(content.encode());
            LogUtil.e("解析出来的数据",s);
            CustomBiaoqingBean customBiaoqingMessage = JSON.parseObject(s, CustomBiaoqingBean.class);
            IntentUtils.intent2BiaoqingPreviewActivity(customBiaoqingMessage.getImgUrl());
        }

    }

    public void bindView(View v, int position, CustomBiaoqingMessage content, UIMessage message) {
        MessageCustomBiaoqingBinding binding =  DataBindingUtil.bind(v);
//        if (message.getMessageDirection() == Message.MessageDirection.SEND) {
//            binding.getRoot().setBackgroundResource(R.drawable.rc_ic_bubble_no_right);
//        } else {
//            binding.getRoot().setBackgroundResource(R.drawable.rc_ic_bubble_no_left);
//        }

        String s = new String(content.encode());
        LogUtil.e("解析出来的数据",s);
        CustomBiaoqingBean customBiaoqingMessage = JSON.parseObject(s, CustomBiaoqingBean.class);

        if(message.getMessageDirection() == Message.MessageDirection.SEND){
            binding.ivImg.setScaleType(ImageView.ScaleType.FIT_END);
        }else{
            binding.ivImg.setScaleType(ImageView.ScaleType.FIT_START);
        }

        if (customBiaoqingMessage.getImgUrl().endsWith(".gif")) {

            ImageUtils.showGif(customBiaoqingMessage.getImgUrl(), binding.ivImg);
        } else {
//            if (content.isDestruct()) {
//                Bitmap bitmap = ImageLoader.getInstance().loadImageSync(customBiaoqingMessage.getImgUrl());
//                if (bitmap != null) {
//                    Bitmap blurryBitmap = BitmapUtil.getBlurryBitmap(v.getContext(), bitmap, 5.0F, 0.25F);
//                    binding.ivImg.setBitmap(blurryBitmap);
//                }
//            } else {
                ImageUtils.showImage(customBiaoqingMessage.getImgUrl(), binding.ivImg);
//            }
        }

//        int progress = message.getProgress();
//        Message.SentStatus status = message.getSentStatus();
//        if (status.equals(Message.SentStatus.SENDING) && progress < 100) {
//            holder.message.setText(progress + "%");
//            holder.message.setVisibility(0);
//        } else {
//            holder.message.setVisibility(8);
//        }

    }



    public Spannable getContentSummary(CustomBiaoqingMessage data) {
        return null;
    }

    public Spannable getContentSummary(Context context, CustomBiaoqingMessage data) {
        return new SpannableString(context.getString(R.string.rc_message_content_image));
    }


}

package com.zonghong.redpacket.utils;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.autonavi.amap.mapcore.FileUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.waw.hr.mutils.LogUtil;
import com.zonghong.redpacket.R;

import java.io.File;

public class ImageUtils {

    public static void showAvatar(String url, ImageView image) {
        Glide.with(image.getContext()).load(url).into(image);
    }

    public static void showImage(int redId, ImageView image) {
        Glide.with(image.getContext()).load(redId).into(image);
    }

    public static void showImage(String redId, ImageView image) {
        Glide.with(image.getContext()).load(redId).into(image);
    }

    public static void showBiaoqing(String redId, ImageView image) {
        Glide.with(image.getContext()).load(redId).into(image);
    }

    public static void showGif(String redId, ImageView image) {
        Glide.with(image.getContext()).load(redId).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(image);

//        ((RequestBuilder)Glide.with(image.getContext()).asGif().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).load(redId).listener(new RequestListener<GifDrawable>() {
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
//                return false;
//            }
//
//            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
//                return false;
//            }
//        }).into(image);
    }
}

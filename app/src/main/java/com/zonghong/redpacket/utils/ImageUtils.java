package com.zonghong.redpacket.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

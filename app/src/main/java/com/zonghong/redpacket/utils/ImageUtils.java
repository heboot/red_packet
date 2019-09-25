package com.zonghong.redpacket.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtils {

    public static void showAvatar(String url, ImageView image) {
        Glide.with(image.getContext()).load(url).into(image);
    }

    public static void showImage(int redId, ImageView image) {
        Glide.with(image.getContext()).load(redId).into(image);
    }

}

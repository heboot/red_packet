package com.zonghong.redpacket.utils;

import android.content.Intent;

import com.zonghong.redpacket.MAPP;


public class IntentUtils {

    private static Intent intent;

    public static void doIntent(Class cls) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), cls);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }


}

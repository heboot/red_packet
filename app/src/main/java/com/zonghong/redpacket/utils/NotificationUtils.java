package com.zonghong.redpacket.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.MainActivity;
import com.zonghong.redpacket.R;

public class NotificationUtils {

    public static void showNoti(int id, String title, String content) {
        //创建一个通知管理器


        Intent intent = new Intent(MAPP.mapp.getCurrentActivity(), MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(MAPP.mapp.getCurrentActivity(), 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationManager notificationManager = (NotificationManager) MAPP.mapp.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(MAPP.mapp, "")
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.icon_app, 1)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(MAPP.mapp.getResources(), R.mipmap.icon_app))
                .build();

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification.sound = uri;


        notificationManager.notify(1, notification);
    }

}

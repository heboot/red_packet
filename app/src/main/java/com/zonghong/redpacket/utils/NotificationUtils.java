package com.zonghong.redpacket.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.MainActivity;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.service.UserService;

import io.rong.imkit.utils.NotificationUtil;

public class NotificationUtils {

    private static NotificationChannel channel, soundChannel, zhendongChannel, zhengdongshengyinChannel;


    public static void showNoti(int id, String title, String content) {
        //创建一个通知管理器
        if (!UserService.getInstance().isTixing()) {
            return;
        }


//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            soundChannel = new NotificationChannel(
                    MAPP.mapp.getApplicationContext().getPackageName() + "sound1",
                    MAPP.mapp.getApplicationContext().getPackageName() + "sound1",
                    NotificationManager.IMPORTANCE_HIGH);

            channel = new NotificationChannel(
                    MAPP.mapp.getApplicationContext().getPackageName() + "nosound1",
                    MAPP.mapp.getApplicationContext().getPackageName() + "nosound1",//这块Android9.0分类的比较完整，你创建多个这样的东西，你可以在设置里边显示那个或者第几个
                    NotificationManager.IMPORTANCE_LOW
            );

            zhendongChannel = new NotificationChannel(
                    MAPP.mapp.getApplicationContext().getPackageName() + "zhendong1",
                    MAPP.mapp.getApplicationContext().getPackageName() + "zhendong1",//这块Android9.0分类的比较完整，你创建多个这样的东西，你可以在设置里边显示那个或者第几个
                    NotificationManager.IMPORTANCE_HIGH
            );
            zhendongChannel.setSound(null,null);
            zhendongChannel.enableVibration(true);
            zhendongChannel.setVibrationPattern(new long[]{500, 500,500,500});

            zhengdongshengyinChannel = new NotificationChannel(
                    MAPP.mapp.getApplicationContext().getPackageName() + "zhendongshengyin1",
                    MAPP.mapp.getApplicationContext().getPackageName() + "zhendongshengyin1",//这块Android9.0分类的比较完整，你创建多个这样的东西，你可以在设置里边显示那个或者第几个
                    NotificationManager.IMPORTANCE_HIGH
            );
            zhengdongshengyinChannel.enableVibration(true);
            zhengdongshengyinChannel.setVibrationPattern(new long[]{500, 500,500,500});
        }


        Intent intent = new Intent(MAPP.mapp.getCurrentActivity(), MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(MAPP.mapp.getCurrentActivity(), 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

//
//        if (UserService.getInstance().isShengyin()) {
//            NotificationUtil.showNotification(MAPP.mapp,title,content,pendingIntent,(int)System.currentTimeMillis(), Notification.DEFAULT_SOUND);
//        }else{
//            NotificationUtil.showNotification(MAPP.mapp,title,content,pendingIntent,(int)System.currentTimeMillis());
//        }
//

        NotificationManager notificationManager = (NotificationManager) MAPP.mapp.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder build = null;

        if (UserService.getInstance().isShengyin() && !UserService.getInstance().isZhendong()) {
            build = new NotificationCompat.Builder(MAPP.mapp, MAPP.mapp.getApplicationContext().getPackageName() + "sound1")
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.icon_app, 1)
                    .setContentIntent(pendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(MAPP.mapp.getResources(), R.mipmap.icon_app));
            build.setOnlyAlertOnce(true);
        } else if (!UserService.getInstance().isShengyin() && !UserService.getInstance().isZhendong()) {
            build = new NotificationCompat.Builder(MAPP.mapp, MAPP.mapp.getApplicationContext().getPackageName() + "nosound1")
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.icon_app, 1)
                    .setContentIntent(pendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(MAPP.mapp.getResources(), R.mipmap.icon_app));
        } else if (!UserService.getInstance().isShengyin() && UserService.getInstance().isZhendong()) {
            build = new NotificationCompat.Builder(MAPP.mapp, MAPP.mapp.getApplicationContext().getPackageName() + "zhendong1")
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.icon_app, 1)
                    .setContentIntent(pendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(MAPP.mapp.getResources(), R.mipmap.icon_app));
        } else if (UserService.getInstance().isShengyin() && UserService.getInstance().isZhendong()) {
            build = new NotificationCompat.Builder(MAPP.mapp, MAPP.mapp.getApplicationContext().getPackageName() + "zhendongshengyin1")
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.icon_app, 1)
                    .setContentIntent(pendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(MAPP.mapp.getResources(), R.mipmap.icon_app));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (UserService.getInstance().isShengyin() && !UserService.getInstance().isZhendong()) {
                notificationManager.createNotificationChannel(soundChannel);
            } else if (!UserService.getInstance().isShengyin() && !UserService.getInstance().isZhendong()) {
                notificationManager.createNotificationChannel(channel);
            } else if (!UserService.getInstance().isShengyin() && UserService.getInstance().isZhendong()) {
                notificationManager.createNotificationChannel(zhendongChannel);
            } else if (UserService.getInstance().isShengyin() && UserService.getInstance().isZhendong()) {
                notificationManager.createNotificationChannel(zhengdongshengyinChannel);
            }
        } else {
            //是否震动
            if (UserService.getInstance().isZhendong()) {
                build.setVibrate(new long[]{1000});
            } else {
                build.setVibrate(new long[]{0});
            }


        }

        Notification notification = build.build();



        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            if (UserService.getInstance().isShengyin()) {
                notification.defaults = Notification.DEFAULT_SOUND;
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notification.sound = uri;
            } else {
                build.setSound(null);
                notification.sound = null;
            }
        }

        //是否震动
        if (UserService.getInstance().isZhendong()) {
            build.setVibrate(new long[]{1000});
            notification.vibrate = new long[]{0};
        } else {
            build.setVibrate(new long[]{0});
            notification.vibrate = null;
        }



        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }

}

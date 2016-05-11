package com.demo.zlm.remoteviewssample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //发送普通通知
    public void sendNotification(View v){
        Intent intent=new Intent(this,Main2.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification=new Notification.Builder(this)
                .setContentText("hello,world")
                .setContentTitle("hello")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("测试")
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);
    }
    //发送自定义通知
    public void sendNotification2(View v){
        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.layout_notification);
        //设置自定义布局的TextView的字体颜色
        remoteViews.setTextColor(R.id.text, Color.RED);
        Intent intent=new Intent(this,Main2.class);
        PendingIntent pend=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //设置点击事件
        remoteViews.setOnClickPendingIntent(R.id.image,pend);
        Notification notification=new Notification.Builder(this)
                .setContent(remoteViews)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("测试2")
                .build();
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(2,notification);
    }
}

package com.demo.zlm.remoteviewssample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private LinearLayout mRemoteViewsContent;
    private BroadcastReceiver mbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RemoteViews remoteViews = intent.getParcelableExtra(MyConstants.EXTRA_REMOTE_VIEWS);
            if (remoteViews != null) {
                updateUI(remoteViews);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRemoteViewsContent = (LinearLayout) findViewById(R.id.remote_views_content);
        IntentFilter filter = new IntentFilter(MyConstants.REMOTE_ACTION);
        registerReceiver(mbReceiver, filter);
    }

    private void updateUI(RemoteViews remoteViews) {
//       View view = remoteViews.apply(this, mRemoteViewsContent);
        int layoutId = getResources().getIdentifier("layout_simulated_notification", "layout", getPackageName());
        View view = getLayoutInflater().inflate(layoutId, mRemoteViewsContent, false);
        remoteViews.reapply(this, view);
        mRemoteViewsContent.addView(view);
    }

    //发送普通通知
    public void sendNotification(View v) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(this, Main2.class));
//        intent.setComponent(new ComponentName(this,"com.demo.zlm.remoteviewssample.Main2"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setContentText("hello,world")
                .setContentTitle("hello")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("测试")
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    //发送自定义通知
    public void sendNotification2(View v) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        //设置自定义布局的TextView的字体颜色
        remoteViews.setTextColor(R.id.text, Color.RED);
        Intent intent = new Intent(this, Main3.class);
        PendingIntent pend = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //设置点击事件
        remoteViews.setOnClickPendingIntent(R.id.image, pend);
        Notification notification = new Notification.Builder(this)
                .setContent(remoteViews)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("测试2")
                .setContentIntent(pend)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(2, notification);
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.button2) {
            Intent intent = new Intent(this, DemoActivity_2.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mbReceiver);
        super.onDestroy();
    }
}

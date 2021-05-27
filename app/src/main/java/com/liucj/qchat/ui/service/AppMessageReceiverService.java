package com.liucj.qchat.ui.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.liucj.factory.Factory;
import com.liucj.factory.data.helper.AccountHelper;
import com.liucj.factory.data.helper.UserHelper;
import com.liucj.factory.model.PushModel;
import com.liucj.factory.model.card.GroupCard;
import com.liucj.factory.model.card.GroupMemberCard;
import com.liucj.factory.model.card.MessageCard;
import com.liucj.factory.model.card.UserCard;
import com.liucj.factory.model.db.User;
import com.liucj.factory.utils.AccountUtil;
import com.liucj.qchat.R;
import com.liucj.qchat.ui.activity.MainActivity;
import com.liucj.qchat.ui.activity.MessageActivity;
import com.liucj.qchat.ui.fragment.contact.ContactFragment;

import java.util.List;

import static com.liucj.factory.Factory.getGson;


/**
 * 个推接收消息的IntentService，用以接收具体的消息信息
 * 替换之前老版本的消息广播
 */
public class AppMessageReceiverService extends GTIntentService {
    @Override
    public void onReceiveServicePid(Context context, int i) {
        // 返回消息接收进程id，当前APP中就是AppPushService进程id
        Log.i(TAG, "onReceiveServicePid() called with: context = [" + context + "], i = [" + i + "]");
    }

    @Override
    public void onReceiveClientId(Context context, String s) {
        // 当设备成功在个推注册时返回个推唯一设备码
        Log.i(TAG, "onReceiveClientId() called with: context = [" + context + "], s = [" + s + "]");
        // 当Id初始化的时候
        // 获取设备Id
        onClientInit(s);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        // 当接收到透传消息时回调，跟之前广播接收消息一样
        Log.i(TAG, "onReceiveMessageData() called with: context = [" + context + "], gtTransmitMessage = [" + gtTransmitMessage + "]");

        // 拿到透传消息的实体信息转换为字符串
        byte[] payload = gtTransmitMessage.getPayload();
        if (payload != null) {
            String message = new String(payload);
            onMessageArrived(message);
        }
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        // 当设备状态变化时回调，是否在线
        Log.i(TAG, "onReceiveOnlineState() called with: context = [" + context + "], b = [" + b + "]");
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
        // 当个推Command命名返回时回调
        Log.i(TAG, "onReceiveCommandResult() called with: context = [" + context + "], gtCmdMessage = [" + gtCmdMessage + "]");
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {
        // 当通知栏消息达到时回调
        Log.i(TAG, "onNotificationMessageArrived() called with: context = [" + context + "], gtNotificationMessage = [" + gtNotificationMessage + "]");
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {
        // 当通知栏消息点击时回调
        Log.i(TAG, "onNotificationMessageClicked() called with: context = [" + context + "], gtNotificationMessage = [" + gtNotificationMessage + "]");
    }


    /**
     * 当Id初始化的试试
     *
     * @param cid 设备Id
     */
    private void onClientInit(String cid) {
        // 设置设备Id
        AccountUtil.setPushId(cid);
        if (AccountUtil.isLogin()) {
            // 账户登录状态，进行一次PushId绑定
            // 没有登录是不能绑定PushId的
            AccountHelper.bindPush(null);
        }
    }

    /**
     * 消息达到时
     *
     * @param message 新消息
     */
    private void onMessageArrived(String message) {
        // 交给Factory处理
        Factory.dispatchPush(message);
        // 首先检查登录状态
        if (!AccountUtil.isLogin())
            return;
        PushModel model = PushModel.decode(message);
        if (model == null)
            return;
        // 对推送集合进行遍历
        for (PushModel.Entity entity : model.getEntities()) {
            Log.e(TAG, "dispatchPush-Entity:" + entity.toString());
            switch (entity.type) {
                case PushModel.ENTITY_TYPE_MESSAGE: {
                    ActivityManager am = (ActivityManager)this.getSystemService(this.ACTIVITY_SERVICE);
                    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                    String clsName = cn.getClassName();
                    if(clsName.equals(MessageActivity.class.getName())){
                        return;
                    }
                    if(clsName.equals(MainActivity.class.getName())){
                        Fragment fragment = MainActivity.mNavHelper.getCurrentFragment();
                        if(fragment != null &&fragment instanceof ContactFragment &&fragment.isVisible())
                            return;
                    }
                    // 普通消息
                    MessageCard card = getGson().fromJson(entity.content, MessageCard.class);
                    User yourUser = UserHelper.findFromNet(card.getSenderId());
                    NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
                    Notification.Builder builder = new Notification.Builder(this);
                    builder.setSmallIcon(R.drawable.ic_logo); // 这里使用的系统默认图标，可自行更换
                    builder.setTicker("您有一条新消息！");
                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo));
                    builder.setContentTitle(yourUser.getName());
                    builder.setContentText(card.getContent());
                    builder.setAutoCancel(true);

                    // 点击后要执行的操作，打开MainActivity
                    Intent intent = new Intent(this, MessageActivity.class);
                    intent.putExtra(MessageActivity.KEY_RECEIVER_ID, yourUser.getId());
                    intent.putExtra(MessageActivity.KEY_RECEIVER_IS_GROUP, card.getGroupId()!=null);
                    PendingIntent pendingIntents = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                    builder.setContentIntent(pendingIntents);

                    // 启动Notification，getNotification()方法已经过时了，不推荐使用，使用build()方法替代
                    notificationManager.notify(1, builder.build());
                    break;
                }
            }
        }
    }
}

package com.liucj.factory.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.liucj.factory.Factory;
import com.liucj.factory.db.User;
import com.liucj.factory.net.api.AccountRspModel;


/**
 *用户的实体类
 */
public class AccountUtil {
    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";
    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";

    // 设备的推送Id
    private static String pushId;
    // 设备Id是否已经绑定到了服务器
    private static boolean isBind;
    // 登录状态的Token，用来接口请求
    private static String token;
    // 登录的用户ID
    private static String userId;
    // 登录的账户
    private static String account;

    private static User user;

    /**
     * 存储数据到XML文件，持久化
     */
    private static void save(Context context) {
        // 获取数据持久化的SP
        SharedPreferences sp = context.getSharedPreferences(AccountUtil.class.getName(),
                Context.MODE_PRIVATE);
        // 存储数据
        sp.edit()
                .putString(KEY_PUSH_ID, pushId)
                .putBoolean(KEY_IS_BIND, isBind)
                .putString(KEY_TOKEN, token)
                .putString(KEY_USER_ID, userId)
                .putString(KEY_ACCOUNT, account)
                .apply();
    }

    /**
     * 进行数据加载
     */
    public static void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(AccountUtil.class.getName(),
                Context.MODE_PRIVATE);
        pushId = sp.getString(KEY_PUSH_ID, "");
        isBind = sp.getBoolean(KEY_IS_BIND, false);
        token = sp.getString(KEY_TOKEN, "");
        userId = sp.getString(KEY_USER_ID, "");
        account = sp.getString(KEY_ACCOUNT, "");
    }


    /**
     * 设置并存储设备的Id
     *
     * @param pushId 设备的推送ID
     */
    public static void setPushId(String pushId) {
        AccountUtil.pushId = pushId;
        AccountUtil.save(Factory.app());
    }

    /**
     * 获取推送Id
     *
     * @return 推送Id
     */
    public static String getPushId() {
        return pushId;
    }
    public static String getPhone(){
        return account;
    }

    /**
     * 返回当前账户是否登录
     *
     * @return True已登录
     */
    public static boolean isLogin() {
        // 用户Id 和 Token 不为空
        return !TextUtils.isEmpty(userId)
                && !TextUtils.isEmpty(token);
    }

    /**
     * 是否已经完善了用户信息
     *
     * @return True 是完成了
     */
    public static boolean isComplete() {
        // 首先保证登录成功
        if (isLogin()) {
//            User self = getUser();
            User self = user;
            return !TextUtils.isEmpty(self.getDesc())
                    && !TextUtils.isEmpty(self.getPortrait())
                    && self.getSex() != 0;
        }
        // 未登录返回信息不完全
        return false;
    }

    /**
     * 是否已经绑定到了服务器
     *
     * @return True已绑定
     */
    public static boolean isBind() {
        return isBind;
    }

    /**
     * 设置绑定状态
     */
    public static void setBind(boolean isBind) {
        AccountUtil.isBind = isBind;
        AccountUtil.save(Factory.app());
    }

    /**
     * 保存我自己的信息到持久化XML中
     *
     * @param model AccountRspModel
     */
    public static void login(AccountRspModel model) {
        // 存储当前登录的账户, token, 用户Id，方便从数据库中查询我的信息
        AccountUtil.token = model.getToken();
        AccountUtil.account = model.getAccount();
        AccountUtil.userId = model.getUser().getId();
        AccountUtil.user = model.getUser();
        save(Factory.app().getApplicationContext());
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return User
     */
//    public static User getUser() {
//        // 如果为null返回一个new的User，其次从数据库查询
//        return TextUtils.isEmpty(userId) ? new User() : SQLite.select()
//                .from(User.class)
//                .where(User_Table.id.eq(userId))
//                .querySingle();
//    }

//    /**
//     * 返回用户Id
//     *
//     * @return 用户Id
//     */
//    public static String getUserId() {
//        return getUser().getId();
//    }

    /**
     * 获取当前登录的Token
     *
     * @return Token
     */
    public static String getToken() {
        return token;
    }
    /**
     * 获取当前登录的Token
     *
     * @return Token
     */
    public static String getUserId() {
        return userId;
    }
    /**
     * 获取当前登录的Token
     *
     * @return Token
     */

    /**
     * 获取当前登录的用户信息
     *
     * @return User
     */
    public static User saveUser(User users) {
//          // 获取数据持久化的SP
        user = users;
        return user;
    }

    public static User getUser() {
        // 如果为null返回一个new的User，其次从数据库查询
        if (TextUtils.isEmpty(userId)){
            return new User();
        }
        return user;
//        return TextUtils.isEmpty(userId) ? new User() : SQLite.select()
//                .from(User.class)
//                .where(User_Table.id.eq(userId))
//                .querySingle();
    }
}

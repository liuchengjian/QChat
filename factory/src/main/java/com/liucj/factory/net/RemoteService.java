package com.liucj.factory.net;


import com.liucj.factory.model.RspModel;
import com.liucj.factory.model.api.account.UserUpdateModel;
import com.liucj.factory.model.api.group.GroupCreateModel;
import com.liucj.factory.model.api.account.LoginBean;
import com.liucj.factory.model.api.message.MsgCreateModel;
import com.liucj.factory.model.api.account.RegisterBean;
import com.liucj.factory.model.card.GroupCard;
import com.liucj.factory.model.card.GroupMemberCard;
import com.liucj.factory.model.card.MessageCard;
import com.liucj.factory.model.card.UserCard;
import com.liucj.factory.model.api.account.AccountRspModel;
import com.liucj.factory.model.db.view.GroupMemberAddModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 网络请求的所有的接口
 */
public interface RemoteService {
    /**
     * 注册接口
     *
     * @param bean 传入的是RegisterBean
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> register(@Body RegisterBean bean);

    /**
     * 登录接口
     *
     * @param bean LoginBean
     * @return RspModel<AccountRspModel>
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>> login(@Body LoginBean bean);

    // 获取联系人列表
    @GET("user/contact")
    Call<RspModel<List<UserCard>>> userContacts();

    // 用户更新的接口
    @PUT("user")
    Call<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);

    // 查询某人的信息
    @GET("user/{userId}")
    Call<RspModel<UserCard>> userFind(@Path("userId") String userId);

    // 用户搜索的接口
    @GET("user/search/{name}")
    Call<RspModel<List<UserCard>>> userSearch(@Path("name") String name);

    // 用户关注接口
    @PUT("user/follow/{userId}")
    Call<RspModel<UserCard>> userFollow(@Path("userId") String userId);
//
//    // 用户更新的接口
//    @PUT("user")
//    Call<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);

    // 发送消息的接口
    @POST("msg")
    Call<RspModel<MessageCard>> msgPush(@Body MsgCreateModel model);

    // 创建群
    @POST("group")
    Call<RspModel<GroupCard>> groupCreate(@Body GroupCreateModel model);

    // 拉取群信息
    @GET("group/{groupId}")
    Call<RspModel<GroupCard>> groupFind(@Path("groupId") String groupId);

    /**
     * 绑定设备Id
     *
     * @param pushId 设备Id
     * @return RspModel<AccountRspModel>
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);


    // 我的群列表
    @GET("group/list/{date}")
    Call<RspModel<List<GroupCard>>> groups(@Path(value = "date", encoded = true) String date);


    // 我的群的成员列表
    @GET("group/{groupId}/member")
    Call<RspModel<List<GroupMemberCard>>> groupMembers(@Path("groupId") String groupId);


    // 群搜索的接口
    @GET("group/search/{name}")
    Call<RspModel<List<GroupCard>>> groupSearch(@Path(value = "name", encoded = true) String name);

    // 给群添加成员
    @POST("group/{groupId}/member")
    Call<RspModel<List<GroupMemberCard>>> groupMemberAdd(@Path("groupId") String groupId,
                                                         @Body GroupMemberAddModel model);


}

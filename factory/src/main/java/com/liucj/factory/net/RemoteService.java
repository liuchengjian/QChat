package com.liucj.factory.net;


import com.liucj.factory.RspModel;
import com.liucj.factory.bean.LoginBean;
import com.liucj.factory.bean.RegisterBean;
import com.liucj.factory.card.UserCard;
import com.liucj.factory.net.api.AccountRspModel;

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
//
//    // 查询某人的信息
//    @GET("user/{userId}")
//    Call<RspModel<UserCard>> userFind(@Path("userId") String userId);
//

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

//    // 发送消息的接口
//    @POST("msg")
//    Call<RspModel<MessageCard>> msgPush(@Body MsgCreateModel model);

}

package com.liucj.factory.model;

import com.liucj.factory.Factory;
import com.liucj.factory.RspModel;
import com.liucj.factory.card.UserCard;
import com.liucj.factory.net.Network;
import com.liucj.factory.net.RemoteService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHelper {

    // 刷新联系人的操作，不需要Callback，直接存储到数据库，
    // 并通过数据库观察者进行通知界面更新，
    // 界面更新的时候进行对比，然后差异更新
    public static void refreshContacts() {
        RemoteService service = Network.remote();
        service.userContacts()
                .enqueue(new Callback<RspModel<List<UserCard>>>() {
                    @Override
                    public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                        RspModel<List<UserCard>> rspModel = response.body();
                        if (rspModel.success()) {
                            // 拿到集合
                            List<UserCard> cards = rspModel.getResult();
                            if (cards == null || cards.size() == 0)
                                return;

                            UserCard[] cards1 = cards.toArray(new UserCard[0]);
                            // CollectionUtil.toArray(cards, UserCard.class);
                            Factory.getUserCenter().dispatch(cards1);
                        } else {
                            Factory.decodeRspCode(rspModel, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                        // nothing
                    }
                });
    }
}

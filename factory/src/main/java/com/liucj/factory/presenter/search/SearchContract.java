package com.liucj.factory.presenter.search;

import com.liucj.factory.model.card.GroupCard;
import com.liucj.factory.model.card.UserCard;
import com.liucj.factory.presenter.base.BaseContract;

import java.util.List;

/**
 * 搜索的契约
 */
public interface SearchContract {

    interface Presenter extends BaseContract.Presenter {
        // 搜索内容
        void search(String content);
    }

    // 搜索人的界面
    interface UserView extends BaseContract.View<Presenter> {
        void onSearchDone(List<UserCard> userCards);
    }

    // 搜索群的界面
    interface GroupView extends BaseContract.View<Presenter> {
        void onSearchDone(List<GroupCard> groupCards);
    }
}

package com.liucj.factory.presenter.contact;

import com.liucj.factory.model.db.User;
import com.liucj.factory.presenter.base.BaseContract;

/**
 * 联系人契约
 */
public interface ContactContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, User> {

    }
}

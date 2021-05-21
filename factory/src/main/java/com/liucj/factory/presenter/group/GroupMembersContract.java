package com.liucj.factory.presenter.group;


import com.liucj.factory.model.db.view.MemberUserModel;
import com.liucj.factory.presenter.base.BaseContract;

/**
 * 群成员的契约
 *
 */
public interface GroupMembersContract {
    interface Presenter extends BaseContract.Presenter {
        // 具有一个刷新的方法
        void refresh();
    }

    // 界面
    interface View extends BaseContract.RecyclerView<Presenter, MemberUserModel> {
        // 获取群的ID
        String getGroupId();
    }
}

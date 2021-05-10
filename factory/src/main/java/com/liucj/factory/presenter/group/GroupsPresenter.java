package com.liucj.factory.presenter.group;

import androidx.recyclerview.widget.DiffUtil;

import com.liucj.factory.data.group.GroupsDataSource;
import com.liucj.factory.data.group.GroupsRepository;
import com.liucj.factory.data.helper.GroupHelper;
import com.liucj.factory.model.db.DiffUiDataCallback;
import com.liucj.factory.model.db.Group;
import com.liucj.factory.presenter.base.BaseSourcePresenter;

import java.util.List;

/**
 * 群组Presenter
 */
public class GroupsPresenter extends BaseSourcePresenter<Group, Group,
        GroupsDataSource, GroupsContract.View> implements GroupsContract.Presenter {
    public GroupsPresenter(GroupsContract.View view) {
        super(new GroupsRepository(),view);
    }
    @Override
    public void start() {
        super.start();

        // 加载网络数据, 以后可以优化到下拉刷新中
        // 只有用户下拉进行网络请求刷新
        GroupHelper.refreshGroups();
    }


    @Override
    public void onDataLoaded(List<Group> groups) {
        final GroupsContract.View view = getView();
        if (view == null)
            return;

        // 对比差异
        List<Group> old = view.getRecyclerAdapter().getItems();
        DiffUiDataCallback<Group> callback = new DiffUiDataCallback<>(old, groups);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 界面刷新
        refreshData(result, groups);
    }
}

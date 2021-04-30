package com.liucj.factory.presenter.contact;

import androidx.recyclerview.widget.DiffUtil;

import com.liucj.common.DataSource;
import com.liucj.common.recycler.BaseRecyclerAdapter;
import com.liucj.factory.bean.BaseSourcePresenter;
import com.liucj.factory.db.DiffUiDataCallback;
import com.liucj.factory.db.User;
import com.liucj.factory.model.UserHelper;
import com.liucj.factory.user.ContactRepository;

import java.util.List;

/**
 * 联系人Presenter
 */
public class ContactPresenter extends BaseSourcePresenter<User, User, ContactDataSource, ContactContract.View>
        implements ContactContract.Presenter, DataSource.SucceedCallback<List<User>> {
    public ContactPresenter(ContactContract.View view) {
        super(new ContactRepository(), view);
    }

    @Override
    public void start() {
        super.start();

        // 加载网络数据
        UserHelper.refreshContacts();
    }

    @Override
    public void onDataLoaded(List<User> users) {
        // 无论怎么操作，数据变更，最终都会通知到这里来
        final ContactContract.View view = getView();
        if (view == null)
            return;
        BaseRecyclerAdapter<User> adapter = view.getRecyclerAdapter();
        List<User> old = adapter.getItems();

        // 进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 调用基类方法进行界面刷新
        refreshData(result, users);
    }
}

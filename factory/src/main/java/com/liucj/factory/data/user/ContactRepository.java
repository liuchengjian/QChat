package com.liucj.factory.data.user;

import com.liucj.common.factory.data.DataSource;
import com.liucj.factory.data.BaseDbRepository;
import com.liucj.factory.model.db.User;
import com.liucj.factory.model.db.User_Table;
import com.liucj.factory.utils.AccountUtil;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * 联系人仓库
 *
 */
public class ContactRepository extends BaseDbRepository<User> implements ContactDataSource {
    @Override
    public void load(DataSource.SucceedCallback<List<User>> callback) {
        super.load(callback);

        // 加载本地数据库数据
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(AccountUtil.getUserId()))
                .orderBy(User_Table.name, true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(User user) {
        return user.isFollow() && !user.getId().equals(AccountUtil.getUserId());
    }
}

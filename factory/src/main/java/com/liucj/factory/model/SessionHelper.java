package com.liucj.factory.model;

import com.liucj.factory.db.Session;
import com.liucj.factory.db.Session_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;


/**
 * 会话辅助工具类
 *
 */
public class SessionHelper {
    // 从本地查询Session
    public static Session findFromLocal(String id) {
        return SQLite.select()
                .from(Session.class)
                .where(Session_Table.id.eq(id))
                .querySingle();
    }
}

package com.liucj.factory.datasoure;

import com.liucj.common.DbDataSource;
import com.liucj.factory.db.Message;
/**
 * 消息的数据源定义，他的实现是：MessageRepository, MessageGroupRepository
 * 关注的对象是Message表
 */
public interface  MessageDataSource extends DbDataSource<Message> {
}

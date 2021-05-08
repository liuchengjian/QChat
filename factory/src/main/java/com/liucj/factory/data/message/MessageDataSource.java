package com.liucj.factory.data.message;

import com.liucj.common.factory.data.DbDataSource;
import com.liucj.factory.model.db.Message;
/**
 * 消息的数据源定义，他的实现是：MessageRepository, MessageGroupRepository
 * 关注的对象是Message表
 */
public interface  MessageDataSource extends DbDataSource<Message> {
}

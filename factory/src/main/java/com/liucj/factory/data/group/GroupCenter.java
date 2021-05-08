package com.liucj.factory.data.group;

import com.liucj.factory.model.card.GroupCard;

/**
 * 群中心的接口定义
 */
public interface GroupCenter {
    // 群卡片的处理
    void dispatch(GroupCard... cards);

    // 群成员的处理
//    void dispatch(GroupMemberCard... cards);
}

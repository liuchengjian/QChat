package com.liucj.factory.presenter.message;

import com.liucj.factory.datasoure.MessageDataSource;
import com.liucj.factory.db.Message;
import com.liucj.factory.db.User;
import com.liucj.factory.model.UserHelper;
import com.liucj.factory.user.MessageRepository;

/**
 * 人聊天的Presenter
 */
public class ChatUserPresenter extends ChatPresenter<ChatContract.UserView>
        implements ChatContract.Presenter {

    public ChatUserPresenter(ChatContract.UserView view, String receiverId) {
        // 数据源，View，接收者，接收者的类型
        super(new MessageRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_NONE);
    }

    @Override
    public void start() {
        super.start();

        // 从本地拿这个人的信息
        User receiver = UserHelper.findFromLocal(mReceiverId);
        getView().onInit(receiver);
    }
}

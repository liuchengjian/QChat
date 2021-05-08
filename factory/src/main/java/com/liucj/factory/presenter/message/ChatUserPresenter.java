package com.liucj.factory.presenter.message;

import com.liucj.factory.model.db.Message;
import com.liucj.factory.model.db.User;
import com.liucj.factory.data.helper.UserHelper;
import com.liucj.factory.data.message.MessageRepository;

/**
 * 人聊天的Presenter
 */
public class ChatUserPresenter extends ChatPresenter<ChatContract.UserView>
        implements ChatContract.Presenter {

    public ChatUserPresenter(ChatContract.UserView view, String receiverId) {
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

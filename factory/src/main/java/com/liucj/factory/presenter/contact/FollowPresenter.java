package com.liucj.factory.presenter.contact;

import com.liucj.common.factory.data.DataSource;
import com.liucj.factory.model.card.UserCard;
import com.liucj.factory.data.helper.UserHelper;
import com.liucj.factory.presenter.base.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * 关注的Presenter
 */
public class FollowPresenter extends BasePresenter<FollowContract.View>
        implements FollowContract.Presenter, DataSource.Callback<UserCard> {

    public FollowPresenter(FollowContract.View view) {
        super(view);
    }
    @Override
    public void follow(String id) {
        start();
        UserHelper.follow(id, this);
    }

    @Override
    public void onDataLoaded(final UserCard userCard) {
        // 成功
        final FollowContract.View view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.onFollowSucceed(userCard);
                }
            });
        }
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final FollowContract.View view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(strRes);
                }
            });
        }
    }


}
